package nuricanozturk.dev.service.payment.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import nuricanozturk.dev.service.payment.entity.Payment;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface IPaymentRepository extends CosmosRepository<Payment, UUID>
{
    Iterable<Payment> findAllByCreationDateAndPharmacyUsername(LocalDate creationDate, String pharmacyUsername);
}
