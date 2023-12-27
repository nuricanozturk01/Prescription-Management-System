package nuricanozturk.dev.service.prescription.service.abstraction;

import nuricanozturk.dev.dto.CreatePharmacyDTO;
import nuricanozturk.dev.dto.ResponseDTO;
import nuricanozturk.dev.service.prescription.dto.LoginDTO;

public interface IAuthenticationService
{
    ResponseDTO createPharmacy(CreatePharmacyDTO createPharmacyDTO);
    ResponseDTO login(LoginDTO loginDTO);
}
