package nuricanozturk.dev.service.prescription.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import nuricanozturk.dev.dto.*;
import nuricanozturk.dev.service.prescription.service.abstraction.IPrescriptionServiceV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.StreamSupport.stream;

@Service("prescriptionServiceV1")
public class PrescriptionServiceV1 implements IPrescriptionServiceV1
{
    private final EnvironmentConfig m_environmentConfig;
    @Value("${cloud.aws.endpoint.uri}")
    private String m_queueName;

    public PrescriptionServiceV1(EnvironmentConfig environmentConfig)
    {
        m_environmentConfig = environmentConfig;
    }

    @Override
    public ResponseDTO getMedicineByName(String name, int page)
    {
        return m_environmentConfig.getMedicineService().getMedicineByName(name, page, null);
    }

    @Override
    public ResponseDTO findCustomerByIdentityNumber(String identityNumber)
    {
        var customer = m_environmentConfig.getCustomerService().findCustomerByIdentityNumber(identityNumber);

        System.out.println(customer.data());

        if (customer.data() == null)
            return new ResponseDTO(-1, -1, -1, "Customer not found.");

        return customer;
    }

    @Override
    public ResponseDTO findCustomersByIdentityNumberContaining(String identityNumber, int page)
    {
        return m_environmentConfig.getCustomerService().findCustomersByIdentityNumberContaining(identityNumber, page);
    }


    @Override
    public ResponseDTO createPrescription(CreatePrescriptionDTO createPrescriptionDTO) throws JsonProcessingException
    {
        var customerResponse = m_environmentConfig.getCustomerService().findCustomerByIdentityNumber(createPrescriptionDTO.customerIdentityNumber());
      /*  var userDTO = m_objectMapper.readValue(customerResponse.data().toString(), CustomerDTO.class);

        if (userDTO == null)
            return new ResponseDTO(-1, -1, -1, "Customer not found.");*/

        var pharmacy = m_environmentConfig.getPharmacyRepository().findPharmacyByUsername(createPrescriptionDTO.pharmacyUsername());

        var totalCost = createPrescriptionDTO.medicines().stream().map(MedicineDTO::price).reduce(Double::sum);

        var paymentDTO = new PaymentDTO(pharmacy.get().getName(), pharmacy.get().getUsername(), createPrescriptionDTO.medicines(), totalCost.get());

        var msg = MessageBuilder
                .withPayload(paymentDTO)
                .build();

        m_environmentConfig.getSqsTemplate().send(m_queueName, msg);

        return new ResponseDTO(1, 1, 1, "Prescription created.");
    }


    @Override
    public List<PharmacyDTO> findAllPharmacies()
    {
        return stream(m_environmentConfig.getPharmacyRepository().findAll().spliterator(), true).map(p -> new PharmacyDTO(p.getName(), p.getUsername(), p.getEmail())).toList();
    }

}