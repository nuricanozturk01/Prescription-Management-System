package nuricanozturk.dev.service.user.repository;

import com.azure.spring.data.cosmos.core.query.CosmosPageImpl;
import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import nuricanozturk.dev.service.user.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICustomerRepository extends CosmosRepository<Customer, UUID>
{
    Optional<Customer> findCustomerByIdentityNumber(String identityNumber);

    CosmosPageImpl<Customer> findCustomersByIdentityNumberContaining(String identityNumber, CosmosPageRequest pageable);
}
