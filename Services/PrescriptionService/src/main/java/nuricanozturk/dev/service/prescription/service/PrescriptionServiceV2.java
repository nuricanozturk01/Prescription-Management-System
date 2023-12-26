package nuricanozturk.dev.service.prescription.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import nuricanozturk.dev.dto.CreatePrescriptionDTO;
import nuricanozturk.dev.dto.PharmacyDTO;
import nuricanozturk.dev.dto.ResponseDTO;
import nuricanozturk.dev.service.prescription.service.abstraction.IPrescriptionServiceV1;
import nuricanozturk.dev.service.prescription.service.abstraction.IPrescriptionServiceV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("prescriptionServiceV2")
public class PrescriptionServiceV2 implements IPrescriptionServiceV2
{
    private final EnvironmentConfig m_environmentConfig;
    private final IPrescriptionServiceV1 m_prescriptionServiceV1;
    @Value("${host.unique-key}")
    private String m_uniqueKey;

    public PrescriptionServiceV2(EnvironmentConfig environmentConfig, IPrescriptionServiceV1 prescriptionServiceV1)
    {
        m_environmentConfig = environmentConfig;
        m_prescriptionServiceV1 = prescriptionServiceV1;
    }

    @Override
    public ResponseDTO getMedicineByName(String name, int page)
    {
        return m_prescriptionServiceV1.getMedicineByName(name, page);
    }

    @Override
    public ResponseDTO findCustomerByIdentityNumber(String identityNumber)
    {
        return m_prescriptionServiceV1.findCustomerByIdentityNumber(identityNumber);
    }

    @Override
    public ResponseDTO findCustomersByIdentityNumberContaining(String identityNumber, int page)
    {
        return m_prescriptionServiceV1.findCustomersByIdentityNumberContaining(identityNumber, page);
    }


    @Override
    public ResponseDTO createPrescription(CreatePrescriptionDTO createPrescriptionDTO) throws JsonProcessingException
    {
        return m_prescriptionServiceV1.createPrescription(createPrescriptionDTO);
    }


    @Override
    public List<PharmacyDTO> findAllPharmacies()
    {
        throw new UnsupportedOperationException();
    }


    @Override
    public ResponseDTO findPharmacyByEmail(String email)
    {
        var pharmacy = m_environmentConfig.getPharmacyRepository().findPharmacyByEmail(email);

        return pharmacy
                .map(value -> new ResponseDTO(1, 1, 1, new PharmacyDTO(value.getName(), value.getUsername(), value.getEmail())))
                .orElseGet(() -> new ResponseDTO(1, 1, 1, null));
    }

    @Override
    public List<PharmacyDTO> findAllPharmacies(String uniqueKey)
    {
        return m_uniqueKey.equals(uniqueKey) ? m_prescriptionServiceV1.findAllPharmacies() : Collections.emptyList();
    }
}