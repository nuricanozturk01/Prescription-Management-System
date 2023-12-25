package nuricanozturk.dev.service.medicine.controller;

import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.service.medicine.entity.Medicine;
import nuricanozturk.dev.service.medicine.repository.IMedicineRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.StreamSupport.stream;

@RestController
@RequestMapping("/api/medicine")
@RequiredArgsConstructor
public class MedicineController
{
    private final IMedicineRepository m_medicineRepository;

    @GetMapping("find/name")
    public List<Medicine> getMedicineByName(@RequestParam("n") String name)
    {
        return stream(m_medicineRepository.findByNameContainsIgnoreCase(name).spliterator(), true).toList();
    }
}
