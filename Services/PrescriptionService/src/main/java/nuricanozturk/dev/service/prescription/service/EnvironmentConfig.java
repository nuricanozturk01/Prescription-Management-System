package nuricanozturk.dev.service.prescription.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import nuricanozturk.dev.service.prescription.config.PharmacyAuthenticationProvider;
import nuricanozturk.dev.service.prescription.repository.IPharmacyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfig
{
    private final IMedicineService m_medicineService;
    private final ICustomerService m_customerService;
    private final IPharmacyRepository m_pharmacyRepository;
    private final PasswordEncoder m_passwordEncoder;
    private final ObjectMapper m_objectMapper;
    private final PharmacyAuthenticationProvider m_authenticationProvider;
    private final SqsTemplate m_sqsTemplate;

    public EnvironmentConfig(IMedicineService medicineService, ICustomerService customerService, IPharmacyRepository pharmacyRepository, PasswordEncoder passwordEncoder, ObjectMapper objectMapper, PharmacyAuthenticationProvider authenticationProvider, SqsTemplate sqsTemplate)
    {
        m_medicineService = medicineService;
        m_customerService = customerService;
        m_pharmacyRepository = pharmacyRepository;
        m_passwordEncoder = passwordEncoder;
        m_objectMapper = objectMapper;
        m_authenticationProvider = authenticationProvider;
        m_sqsTemplate = sqsTemplate;
    }

    public IMedicineService getMedicineService()
    {
        return m_medicineService;
    }

    public ICustomerService getCustomerService()
    {
        return m_customerService;
    }

    public IPharmacyRepository getPharmacyRepository()
    {
        return m_pharmacyRepository;
    }

    public PasswordEncoder getPasswordEncoder()
    {
        return m_passwordEncoder;
    }

    public ObjectMapper getObjectMapper()
    {
        return m_objectMapper;
    }

    public PharmacyAuthenticationProvider getAuthenticationProvider()
    {
        return m_authenticationProvider;
    }

    public SqsTemplate getSqsTemplate()
    {
        return m_sqsTemplate;
    }
}
