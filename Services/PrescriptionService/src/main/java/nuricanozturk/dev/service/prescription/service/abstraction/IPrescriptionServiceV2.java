package nuricanozturk.dev.service.prescription.service.abstraction;

import nuricanozturk.dev.dto.PharmacyDTO;
import nuricanozturk.dev.dto.ResponseDTO;

import java.util.List;

public interface IPrescriptionServiceV2 extends IPrescriptionService
{
    ResponseDTO findPharmacyByEmail(String email);
    List<PharmacyDTO> findAllPharmacies(String uniqueKey);
}
