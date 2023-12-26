package nuricanozturk.dev.service.medicine.repository;


import com.azure.spring.data.cosmos.core.query.CosmosPageImpl;
import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import nuricanozturk.dev.service.medicine.entity.Medicine;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicineRepository extends CosmosRepository<Medicine, String>
{

    CosmosPageImpl<Medicine> findByNameContainsIgnoreCase(String name, CosmosPageRequest pageable);
}
