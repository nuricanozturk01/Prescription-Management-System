package nuricanozturk.dev.service.user.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.CosmosUniqueKey;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Container(containerName = "customer")
@Getter
@Setter
public class Customer
{
    @Id
    private UUID customerId;
    @PartitionKey
    private String identityNumber;
    private String customerName;
    private String customerSurname;


    public Customer(String customerName, String customerSurname, String identityNumber)
    {
        customerId = UUID.randomUUID();
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.identityNumber = identityNumber;
    }

    public String getFullName()
    {
        return customerName + " " + customerSurname;
    }
}
