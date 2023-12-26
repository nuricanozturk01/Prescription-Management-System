package nuricanozturk.dev.service.user.controller;

import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.service.user.dto.CustomerSaveDTO;
import nuricanozturk.dev.service.user.dto.ResponseDTO;
import nuricanozturk.dev.service.user.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
public class CustomerController
{
    private final CustomerService m_customerService;

    @PostMapping("/save")
    public ResponseDTO saveCustomer(@RequestBody CustomerSaveDTO customerSaveDTO)
    {
        try
        {
            return m_customerService.saveCustomer(customerSaveDTO);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseDTO(0, 0, 0, e.getMessage());
        }
    }

    @GetMapping("/find/identity")
    public ResponseDTO findCustomerByIdentityNumber(@RequestParam("id") String identityNumber)
    {
        try
        {
            return m_customerService.getCustomerByIdentityNumber(identityNumber);
        } catch (Exception e)
        {
            return new ResponseDTO(0, 0, 0, e.getMessage());
        }
    }

    @GetMapping("/find/identity/containing")
    public ResponseDTO findCustomersByIdentityNumberContaining(@RequestParam("id") String identityNumber, @RequestParam("page") int page)
    {
        try
        {
            return m_customerService.getCustomersByIdentityNumberContaining(identityNumber, page);
        } catch (Exception e)
        {
            return new ResponseDTO(0, 0, 0, e.getMessage());
        }
    }
}
