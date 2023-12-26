package nuricanozturk.dev.service.medicine.controller;

import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.service.medicine.dto.ResponseDTO;
import nuricanozturk.dev.service.medicine.service.MedicineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medicine")
@RequiredArgsConstructor
public class MedicineController
{
    private final MedicineService m_medicineService;


    @GetMapping("find/name")
    public ResponseDTO getMedicineByName(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam(required = false) String continuationToken)
    {
        try
        {
            return m_medicineService.findMedicinesByNameContainsIgnoreCase(name, page, continuationToken);
        } catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseDTO(0, 0, 0, e.getMessage());
        }
    }
}

