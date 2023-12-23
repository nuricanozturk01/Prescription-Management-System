package nuricanozturk.dev.service.medicine.service;

import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.service.medicine.ExcelReader;
import nuricanozturk.dev.service.medicine.FileDownloader;
import nuricanozturk.dev.service.medicine.repository.IMedicineRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerService
{
    private final IMedicineRepository m_medicineRepository;
    private final ExcelReader m_excelReader;
    private final FileDownloader m_fileDownloader;

    //@Scheduled(cron = "00 01 * * 0")
    @Scheduled(cron = "0 02 00 * * SUN")
    public void scheduleTask()
    {
        m_fileDownloader.downloadFile();
        m_excelReader.readExcelFile("latest_medicine_list.xlsx", m_medicineRepository::save);
        System.out.println("Medicine list updated successfully!");
    }
}
