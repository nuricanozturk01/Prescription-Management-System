package nuricanozturk.dev.service.user.service;

import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import nuricanozturk.dev.service.user.dto.CustomerDTO;
import nuricanozturk.dev.service.user.dto.CustomerSaveDTO;
import nuricanozturk.dev.service.user.dto.CustomersDTO;
import nuricanozturk.dev.service.user.dto.ResponseDTO;
import nuricanozturk.dev.service.user.entity.Customer;
import nuricanozturk.dev.service.user.repository.ICustomerRepository;
import org.springframework.stereotype.Service;

import static java.util.stream.StreamSupport.stream;

@Service
public class CustomerService
{
    private final ICustomerRepository m_customerRepository;

    public CustomerService(ICustomerRepository customerRepository)
    {
        m_customerRepository = customerRepository;
    }

    public ResponseDTO getCustomerByIdentityNumber(String identityNumber)
    {
        try
        {
            var customer = m_customerRepository.findCustomerByIdentityNumber(identityNumber);
            return customer.map(value -> new ResponseDTO(1, 1, 1,
                            new CustomerDTO(value.getIdentityNumber(), value.getFullName())))
                    .orElseGet(() -> new ResponseDTO(1, 1, 1, null));
        } catch (Exception e)
        {
            return new ResponseDTO(1, 1, 1, e.getMessage());
        }
    }

    public ResponseDTO getCustomersByIdentityNumberContaining(String identityNumber, int page)
    {
        try
        {
            var pageable = new CosmosPageRequest(page - 1, 15, null);

            var customers = m_customerRepository.findCustomersByIdentityNumberContaining(identityNumber, pageable);

            var customersDTO = stream(customers.spliterator(), true)
                    .map(customer -> new CustomerDTO(customer.getIdentityNumber(),
                            customer.getCustomerName() + " " + customer.getCustomerSurname()))
                    .toList();

            return new ResponseDTO(page, customers.getTotalPages(), customers.getNumberOfElements(), new CustomersDTO(customersDTO));
        } catch (Exception e)
        {
            return new ResponseDTO(1, 1, 1, e.getMessage());
        }
    }

    public ResponseDTO saveCustomer(CustomerSaveDTO customerSaveDTO)
    {
        if (customerSaveDTO.name() == null || customerSaveDTO.surname() == null || customerSaveDTO.identityNumber() == null)
            return new ResponseDTO(-1, -1, -1, "Customer name, surname and identity number cannot be null.");

        if (customerSaveDTO.name().isBlank() || customerSaveDTO.surname().isBlank() || customerSaveDTO.identityNumber().isBlank())
            return new ResponseDTO(-1, -1, -1, "Customer name, surname and identity number cannot be empty.");


        if (customerSaveDTO.name().isEmpty() || customerSaveDTO.surname().isEmpty())
            return new ResponseDTO(-1, -1, -1, "Customer name and surname cannot be empty.");


        if (customerSaveDTO.identityNumber().trim().replaceAll("\\s+", "").length() != 11)
            return new ResponseDTO(-1, -1, -1, "Identity number must be 11 digits.");

        var search = m_customerRepository.findCustomerByIdentityNumber(customerSaveDTO.identityNumber());

        if (search.isPresent())
            return new ResponseDTO(-1, -1, -1, "Customer already exists.");

        var savedCustomer = m_customerRepository.save(new Customer(customerSaveDTO.name(), customerSaveDTO.surname(), customerSaveDTO.identityNumber()));

        return new ResponseDTO(1, 1, 1, savedCustomer);
    }
}
