package nuricanozturk.dev.service.payment.service;

import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.dto.PharmacyDTO;
import nuricanozturk.dev.service.payment.config.EmailTopic;
import nuricanozturk.dev.service.payment.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.lang.String.format;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ZScoreSchedulerService
{
    private final IPharmacyService m_pharmacyService;
    private final ExecutorService m_executorService;
    private final PaymentService m_paymentService;
    private final EmailService m_emailService;

    @Value("${prescription.service.unique-key}")
    private String m_uniqueKey;

    //@Scheduled(cron = "0 37 01 * * *", zone = "Europe/Istanbul")
    @Scheduled(cron = "0 00 13 * * *", zone = "Europe/Istanbul")
    //@Scheduled(cron = "0 */5 * * * ?", zone = "Europe/Istanbul")
    public void schedule()
    {
        var pharmacies = m_pharmacyService.findAllPharmacies(m_uniqueKey);

        pharmacies.forEach(pharmacy -> m_executorService.execute(() -> calculateZScoreAndSendEmail(pharmacy)));
    }

    private void calculateZScoreAndSendEmail(PharmacyDTO pharmacy)
    {
        //var date = LocalDate.now().minusDays(1L);
        var istanbulZone = ZoneId.of("Europe/Istanbul");

        var dateOneDayAgoInIstanbul = LocalDate.now(istanbulZone).minusDays(1L);

        System.out.println(dateOneDayAgoInIstanbul.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        var payments = m_paymentService.findAllByCreationDateAndPharmacyUsername(dateOneDayAgoInIstanbul, pharmacy.username());

        var zScore = calculateZScore(payments);

        var emailTopic = new EmailTopic(pharmacy.name() + " Z-Score", pharmacy.email(), format("Total Amount is: %.2f TL", zScore));

        m_emailService.sendEmail(emailTopic);
    }

    private double calculateZScore(List<Payment> payments)
    {
        if (payments.isEmpty())
            return 0.0;

        return payments.stream().map(Payment::getTotalPrice).reduce(Double::sum).get();
    }
}
