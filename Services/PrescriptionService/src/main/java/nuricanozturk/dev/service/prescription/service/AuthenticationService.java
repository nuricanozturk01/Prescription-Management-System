package nuricanozturk.dev.service.prescription.service;

import nuricanozturk.dev.dto.CreatePharmacyDTO;
import nuricanozturk.dev.dto.ResponseDTO;
import nuricanozturk.dev.service.prescription.dto.AuthenticationResponse;
import nuricanozturk.dev.service.prescription.dto.LoginDTO;
import nuricanozturk.dev.service.prescription.entity.Pharmacy;
import nuricanozturk.dev.service.prescription.service.abstraction.IAuthenticationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static callofproject.dev.service.jwt.JwtUtil.generateToken;

@Service
public class AuthenticationService implements IAuthenticationService
{
    private final EnvironmentConfig m_environmentConfig;

    public AuthenticationService(EnvironmentConfig environmentConfig)
    {
        m_environmentConfig = environmentConfig;
    }

    public ResponseDTO login(LoginDTO loginDTO)
    {
        var auth = m_environmentConfig.getAuthenticationProvider().authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));

        if (!auth.isAuthenticated())
            return new ResponseDTO(-1, -1, -1, "Invalid username or password.");
        var pharmacy = m_environmentConfig.getPharmacyRepository().findPharmacyByUsername(loginDTO.username()).get();
        return new ResponseDTO(1, 1, 1, new AuthenticationResponse(pharmacy.getName(),
                pharmacy.getUsername(), generateToken(loginDTO.username())));
    }

    public ResponseDTO createPharmacy(CreatePharmacyDTO createPharmacyDTO)
    {
        if (createPharmacyDTO.name() == null || createPharmacyDTO.email() == null || createPharmacyDTO.username() == null || createPharmacyDTO.password() == null)
            return new ResponseDTO(-1, -1, -1, "Customer name, surname and identity number cannot be null.");

        if (createPharmacyDTO.name().isBlank() || createPharmacyDTO.email().isBlank() || createPharmacyDTO.username().isBlank() || createPharmacyDTO.password().isBlank())
            return new ResponseDTO(-1, -1, -1, "Customer name, surname and identity number cannot be empty.");

        if (createPharmacyDTO.name().isEmpty() || createPharmacyDTO.email().isEmpty() || createPharmacyDTO.username().isEmpty() || createPharmacyDTO.password().isEmpty())
            return new ResponseDTO(-1, -1, -1, "Customer name and surname cannot be empty.");

        if (m_environmentConfig.getPharmacyRepository().existsByUsernameOrEmail(createPharmacyDTO.username(), createPharmacyDTO.email()))
            return new ResponseDTO(-1, -1, -1, "pharmacy already exists.");

        var pharmacy = new Pharmacy(createPharmacyDTO.name(),
                createPharmacyDTO.username(),
                createPharmacyDTO.email(),
                m_environmentConfig.getPasswordEncoder().encode(createPharmacyDTO.password()));

        var savedPharmacy = m_environmentConfig.getPharmacyRepository().save(pharmacy);

        return new ResponseDTO(1, 1, 1, savedPharmacy);
    }

    @CacheEvict(value = "pharmacy", key = "#username")
    public void evictCache(String username)
    {
    }

}
