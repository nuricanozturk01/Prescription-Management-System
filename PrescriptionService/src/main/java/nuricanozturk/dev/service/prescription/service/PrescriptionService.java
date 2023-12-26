package nuricanozturk.dev.service.prescription.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import nuricanozturk.dev.dto.*;
import nuricanozturk.dev.service.prescription.entity.Pharmacy;
import nuricanozturk.dev.service.prescription.repository.IPharmacyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.StreamSupport.stream;

@Service
public class PrescriptionService
{
    private final IMedicineService m_medicineService;
    private final ICustomerService m_customerService;
    private final IPharmacyRepository m_pharmacyRepository;
    private final ObjectMapper m_objectMapper;
    private final SqsTemplate m_sqsTemplate;

    @Value("${cloud.aws.endpoint.uri}")
    private String m_queueName;


    public PrescriptionService(IMedicineService medicineService, ICustomerService customerService, IPharmacyRepository pharmacyRepository, ObjectMapper objectMapper, SqsTemplate sqsTemplate)
    {
        m_medicineService = medicineService;
        m_customerService = customerService;
        m_pharmacyRepository = pharmacyRepository;
        m_objectMapper = objectMapper;
        m_sqsTemplate = sqsTemplate;
    }

    public ResponseDTO getMedicineByName(String name, int page)
    {
        return m_medicineService.getMedicineByName(name, page, null);
    }

    public ResponseDTO findCustomerByIdentityNumber(String identityNumber)
    {
        return m_customerService.findCustomerByIdentityNumber(identityNumber);
    }

    public ResponseDTO findCustomersByIdentityNumberContaining(String identityNumber, int page)
    {
        return m_customerService.findCustomersByIdentityNumberContaining(identityNumber, page);
    }


    public ResponseDTO createPrescription(CreatePrescriptionDTO createPrescriptionDTO) throws JsonProcessingException
    {
        var customerResponse = m_customerService.findCustomerByIdentityNumber(createPrescriptionDTO.customerIdentityNumber());
      /*  var userDTO = m_objectMapper.readValue(customerResponse.data().toString(), CustomerDTO.class);

        if (userDTO == null)
            return new ResponseDTO(-1, -1, -1, "Customer not found.");*/

        var pharmacy = m_pharmacyRepository.findPharmacyByUsername(createPrescriptionDTO.pharmacyUsername());

        var totalCost = createPrescriptionDTO.medicines().stream().map(MedicineDTO::price).reduce(Double::sum);

        var paymentDTO = new PaymentDTO(pharmacy.get().getName(), pharmacy.get().getUsername(), createPrescriptionDTO.medicines(), totalCost.get());

        var msg = MessageBuilder
                .withPayload(paymentDTO)
                .build();

        m_sqsTemplate.send(m_queueName, msg);

        return new ResponseDTO(1, 1, 1, "Prescription created.");
    }

    public ResponseDTO createPharmacy(CreatePharmacyDTO createPharmacyDTO)
    {
        if (createPharmacyDTO.name() == null || createPharmacyDTO.email() == null || createPharmacyDTO.username() == null || createPharmacyDTO.password() == null)
            return new ResponseDTO(-1, -1, -1, "Customer name, surname and identity number cannot be null.");

        if (createPharmacyDTO.name().isBlank() || createPharmacyDTO.email().isBlank() || createPharmacyDTO.username().isBlank() || createPharmacyDTO.password().isBlank())
            return new ResponseDTO(-1, -1, -1, "Customer name, surname and identity number cannot be empty.");

        if (createPharmacyDTO.name().isEmpty() || createPharmacyDTO.email().isEmpty() || createPharmacyDTO.username().isEmpty() || createPharmacyDTO.password().isEmpty())
            return new ResponseDTO(-1, -1, -1, "Customer name and surname cannot be empty.");

        if (m_pharmacyRepository.existsByUsernameOrEmail(createPharmacyDTO.username(), createPharmacyDTO.email()))
            return new ResponseDTO(-1, -1, -1, "pharmacy already exists.");

        var pharmacy = new Pharmacy(createPharmacyDTO.name(), createPharmacyDTO.username(), createPharmacyDTO.email(), createPharmacyDTO.password());
        var savedPharmacy = m_pharmacyRepository.save(pharmacy);

        return new ResponseDTO(1, 1, 1, savedPharmacy);
    }

    public List<PharmacyDTO> findAllPharmacies()
    {
        return stream(m_pharmacyRepository.findAll().spliterator(), true).map(p -> new PharmacyDTO(p.getName(), p.getUsername(), p.getEmail())).toList();
    }
}
