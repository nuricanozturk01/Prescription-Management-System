package nuricanozturk.dev.service.medicine.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@Container(containerName = "medicine")
public class Medicine
{
    @Id
    private String name;
    @PartitionKey
    private String barcode;
    private String atcCode;
    private String atcName;
    private String firmName;
    private String prescriptionType;

    public Medicine(String name, String barcode, String atcCode, String atcName, String firmName, String prescriptionType)
    {
        this.name = name;
        this.barcode = barcode;
        this.atcCode = atcCode;
        this.atcName = atcName;
        this.firmName = firmName;
        this.prescriptionType = prescriptionType;
    }

    @Override
    public String toString()
    {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", atcCode='" + atcCode + '\'' +
                ", atcName='" + atcName + '\'' +
                ", firmName='" + firmName + '\'' +
                ", prescriptionType='" + prescriptionType + '\'' +
                '}';
    }
}