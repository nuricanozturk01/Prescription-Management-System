package nuricanozturk.dev.service.medicine.repository;

import nuricanozturk.dev.service.medicine.entity.Medicine;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface IMedicineRepository extends JpaRepository<Medicine, String>
{

}
