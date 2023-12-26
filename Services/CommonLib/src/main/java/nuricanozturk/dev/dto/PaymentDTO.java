package nuricanozturk.dev.dto;

import java.io.Serializable;
import java.util.List;

public record PaymentDTO(String pharmacyName, String pharmacyUsername, List<MedicineDTO> medicines, double totalPrice) implements Serializable
{
}
