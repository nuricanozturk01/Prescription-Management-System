package nuricanozturk.dev.service.prescription.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import nuricanozturk.dev.service.prescription.entity.Pharmacy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPharmacyRepository extends CosmosRepository<Pharmacy, UUID>
{
    @Cacheable(value = "pharmacy", key = "#username")
    Optional<Pharmacy> findPharmacyByUsername(String username);

    Optional<Pharmacy> findPharmacyByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);
}
