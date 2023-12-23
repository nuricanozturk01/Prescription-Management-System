package nuricanozturk.dev.service.medicine;

import nuricanozturk.dev.service.medicine.entity.Medicine;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

@Component
@Lazy
public class ExcelReader
{
    public void readExcelFile(String filePath, Consumer<Medicine> consumer)
    {
        // Try with resources closes the file automatically
        try (var file = new FileInputStream(filePath); var workbook = new XSSFWorkbook(file))
        {
            var sheet = workbook.getSheetAt(0);

            for (Row row : sheet)
            {
                if (row.getRowNum() >= 0 && row.getRowNum() <= 2)
                    continue;

                var medicine = new Medicine(
                        getCellValue(row, 0),
                        getCellValue(row, 1),
                        getCellValue(row, 2),
                        getCellValue(row, 3),
                        getCellValue(row, 4),
                        getCellValue(row, 5)
                );
                consumer.accept(medicine);
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private String getCellValue(Row row, int cellNumber)
    {
        var cell = row.getCell(cellNumber);
        if (cell != null)
        {
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue();
        }
        return "";
    }
}