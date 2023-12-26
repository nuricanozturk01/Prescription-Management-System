package nuricanozturk.dev.service.prescription.service;

import nuricanozturk.dev.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "medicine-service", url = "https://medicine-scheduler-service.azurewebsites.net/api/medicine")
public interface IMedicineService
{
    @GetMapping("find/name")
    ResponseDTO getMedicineByName(@RequestParam("name") String name,
                                  @RequestParam("page") int page,
                                  @RequestParam(required = false) String continuationToken);
}
