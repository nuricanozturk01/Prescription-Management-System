package nuricanozturk.dev.dto;

import java.io.Serializable;

public record CustomerDTO(long identityNumber, String fullName) implements Serializable
{
}
