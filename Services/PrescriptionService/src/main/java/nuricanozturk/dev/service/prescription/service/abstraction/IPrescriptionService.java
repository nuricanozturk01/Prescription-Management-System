package nuricanozturk.dev.service.prescription.service.abstraction;

import com.fasterxml.jackson.core.JsonProcessingException;
import nuricanozturk.dev.dto.CreatePharmacyDTO;
import nuricanozturk.dev.dto.CreatePrescriptionDTO;
import nuricanozturk.dev.dto.PharmacyDTO;
import nuricanozturk.dev.dto.ResponseDTO;
import nuricanozturk.dev.service.prescription.dto.LoginDTO;

import java.util.List;

public interface IPrescriptionService
{
    ResponseDTO createPrescription(CreatePrescriptionDTO createPrescriptionDTO) throws JsonProcessingException;

    ResponseDTO getMedicineByName(String name, int page);

    ResponseDTO findCustomerByIdentityNumber(String identityNumber);

    ResponseDTO findCustomersByIdentityNumberContaining(String identityNumber, int page);

    List<PharmacyDTO> findAllPharmacies();


}
