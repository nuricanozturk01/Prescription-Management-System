package nuricanozturk.dev.service.prescription.service;


import nuricanozturk.dev.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customer-service", url = "https://customers-server.azurewebsites.net/api/customer")
public interface ICustomerService
{
    @GetMapping("/find/identity")
    ResponseDTO findCustomerByIdentityNumber(@RequestParam("id") String identityNumber);

    @GetMapping("/find/identity/containing")
    ResponseDTO findCustomersByIdentityNumberContaining(@RequestParam("id") String identityNumber,
                                                        @RequestParam("page") int page);
}
