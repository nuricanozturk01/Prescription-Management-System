package nuricanozturk.dev.service.medicine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "medicine")
public class Medicine
{
    @Id
    private String name;
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "atc_code")
    private String atcCode;
    @Column(name = "atc_name")
    private String atcName;
    @Column(name = "firm_name")
    private String firmName;
    @Column(name = "prescription_type")
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