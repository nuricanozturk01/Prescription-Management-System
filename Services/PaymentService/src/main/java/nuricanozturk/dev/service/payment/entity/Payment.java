package nuricanozturk.dev.service.payment.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nuricanozturk.dev.dto.MedicineDTO;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Container(containerName = "payment")
@RequiredArgsConstructor
@Getter
@Setter
public class Payment
{
    @Id
    private UUID paymentId;
    @PartitionKey
    private String pharmacyUsername;
    private String pharmacyName;
    private List<MedicineDTO> medicines;
    double totalPrice;
    private LocalDate creationDate;

    public Payment(String pharmacyUsername, String pharmacyName, List<MedicineDTO> medicines, double totalPrice)
    {
        creationDate = LocalDate.now();
        this.paymentId = UUID.randomUUID();
        this.pharmacyUsername = pharmacyUsername;
        this.pharmacyName = pharmacyName;
        this.medicines = medicines;
        this.totalPrice = totalPrice;
    }
}
