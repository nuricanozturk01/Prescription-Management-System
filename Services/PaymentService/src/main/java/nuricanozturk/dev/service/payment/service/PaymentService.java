package nuricanozturk.dev.service.payment.service;


import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.dto.PaymentDTO;
import nuricanozturk.dev.service.payment.entity.Payment;
import nuricanozturk.dev.service.payment.repository.IPaymentRepository;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.StreamSupport.stream;


@Service
@RequiredArgsConstructor
public class PaymentService
{
    private final IPaymentRepository m_paymentRepository;

    @SqsListener(value = "payment_queue")
    public void receiveMessages(Message<PaymentDTO> paymentDTO)
    {
        var payment = new Payment(paymentDTO.getPayload().pharmacyUsername(), paymentDTO.getPayload().pharmacyName(),
                paymentDTO.getPayload().medicines(), paymentDTO.getPayload().totalPrice());

        m_paymentRepository.save(payment);

        System.out.println("payment saved");
    }


    public List<Payment> findAllByCreationDateAndPharmacyUsername(LocalDate date, String pharmacyUsername)
    {
        return stream(m_paymentRepository.findAllByCreationDateAndPharmacyUsername(date, pharmacyUsername).spliterator(), true).toList();
    }
}
