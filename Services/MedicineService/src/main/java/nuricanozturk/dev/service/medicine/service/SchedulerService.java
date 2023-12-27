package nuricanozturk.dev.service.medicine.service;

import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.service.medicine.ExcelReader;
import nuricanozturk.dev.service.medicine.FileDownloader;
import nuricanozturk.dev.service.medicine.config.EmailTopic;
import nuricanozturk.dev.service.medicine.repository.IMedicineRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerService
{
    private final EmailService m_emailService;
    private final IMedicineRepository m_medicineRepository;
    private final ExcelReader m_excelReader;
    private final FileDownloader m_fileDownloader;


    //@Scheduled(cron = "0 17 19 * * MON")
    @Scheduled(cron = "0 30 13 * * WED", zone = "Europe/Istanbul")
    //@Scheduled(cron = "0 */1 * * * ?", zone = "Europe/Istanbul")
    public void scheduleTask()
    {
        try
        {
            m_fileDownloader.downloadFile();
            var list =Files.list(Paths.get(System.getProperty("user.dir")))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.joining("\n"));

            var startTopic = new EmailTopic("Medicine Update operation started!", "nuricanozturk01@gmail.com", list);

            m_emailService.sendEmail(startTopic);

            var start = System.currentTimeMillis();

            m_excelReader.readExcelFile(Paths.get(System.getProperty("user.dir") + "/latest_medicine_list.xlsx"), m_medicineRepository::save);

            var end = System.currentTimeMillis();

            var duration = TimeUnit.MINUTES.convert(end - start, TimeUnit.MILLISECONDS);

            var message = "Medicine list has been updated in " + duration + " minutes.";
            var emailTopic = new EmailTopic("Medicines Updated", "nuricanozturk01@gmail.com", message);

            m_emailService.sendEmail(emailTopic);

        } catch (Exception e)
        {
            e.printStackTrace();
            var emailTopic = new EmailTopic("Medicines Exception", "nuricanozturk01@gmail.com", e.getMessage());
            m_emailService.sendEmail(emailTopic);
        }
    }
}
