package nuricanozturk.dev.dto;

import java.io.Serializable;

public record MedicineDTO(String name, String prescription, double price) implements Serializable
{
}
