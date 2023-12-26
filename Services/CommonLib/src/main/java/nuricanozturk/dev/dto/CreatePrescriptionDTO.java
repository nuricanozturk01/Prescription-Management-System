package nuricanozturk.dev.dto;

import java.io.Serializable;
import java.util.List;

    public record CreatePrescriptionDTO(String pharmacyName, String pharmacyUsername, String customerIdentityNumber, String customerFullName,
                                    List<MedicineDTO> medicines, double totalPrice) implements Serializable
{
}
