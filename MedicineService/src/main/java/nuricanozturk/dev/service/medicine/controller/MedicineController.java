package nuricanozturk.dev.service.medicine.controller;

import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.service.medicine.dto.ResponseDTO;
import nuricanozturk.dev.service.medicine.repository.IMedicineRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medicine")
@RequiredArgsConstructor
public class MedicineController
{
    private final IMedicineRepository m_medicineRepository;

    @GetMapping("find/name")
    public ResponseDTO getMedicineByName(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam(required = false) String continuationToken)
    {
        CosmosPageRequest pageable = new CosmosPageRequest(page - 1, 15, continuationToken);
        var result = m_medicineRepository.findByNameContainsIgnoreCase(name, pageable);
        return new ResponseDTO(page, result.getTotalPages(), result.getNumberOfElements(), result.getContent());
    }
}

