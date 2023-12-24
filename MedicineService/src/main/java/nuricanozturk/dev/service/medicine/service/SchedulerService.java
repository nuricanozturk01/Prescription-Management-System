package nuricanozturk.dev.service.medicine.service;

import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.service.medicine.ExcelReader;
import nuricanozturk.dev.service.medicine.FileDownloader;
import nuricanozturk.dev.service.medicine.config.EmailTopic;
import nuricanozturk.dev.service.medicine.repository.IMedicineRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerService
{
    private final EmailService m_emailService;
    private final IMedicineRepository m_medicineRepository;
    private final ExcelReader m_excelReader;
    private final FileDownloader m_fileDownloader;


    @Scheduled(cron = "0 50 22 * * SUN")
    //@Scheduled(cron = "0 00 02 * * TUE")
    public void scheduleTask()
    {
        m_fileDownloader.downloadFile();

        var start = System.currentTimeMillis();
        System.out.println("start saving to database");
        m_excelReader.readExcelFile("latest_medicine_list.xlsx", m_medicineRepository::saveAndFlush);
        var end = System.currentTimeMillis();
        System.out.println("finish saving to database");
        var duration = TimeUnit.MINUTES.convert(end - start, TimeUnit.MILLISECONDS);
        var message = "Medicine list has been updated in " + duration + " minutes.";
        var emailTopic = new EmailTopic("Medicines Updated", "nuricanozturk01@gmail.com", message);
        m_emailService.sendEmail(emailTopic);
    }
}
