package nuricanozturk.dev.service.prescription.controller;

import nuricanozturk.dev.dto.CreatePharmacyDTO;
import nuricanozturk.dev.dto.ResponseDTO;
import nuricanozturk.dev.service.prescription.dto.LoginDTO;
import nuricanozturk.dev.service.prescription.service.abstraction.IAuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController
{
    private final IAuthenticationService m_authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService)
    {
        m_authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO)
    {
        try
        {
            return m_authenticationService.login(loginDTO);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    @PostMapping("/register")
    public ResponseDTO createPharmacy(@RequestBody CreatePharmacyDTO createPharmacyDTO)
    {
        try
        {
            return m_authenticationService.createPharmacy(createPharmacyDTO);
        }
        catch (Exception e)
        {
            return new ResponseDTO(-1, -1, -1, e.getMessage());
        }
    }
}
