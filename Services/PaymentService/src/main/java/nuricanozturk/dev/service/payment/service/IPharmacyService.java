package nuricanozturk.dev.service.payment.service;

import nuricanozturk.dev.dto.PharmacyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "pharmacy-service", url = "${pharmacy.service.url}")
public interface IPharmacyService
{
    @GetMapping("/find/pharmacies/all")
    List<PharmacyDTO> findAllPharmacies(@RequestParam("key") String uniqueKey);
}