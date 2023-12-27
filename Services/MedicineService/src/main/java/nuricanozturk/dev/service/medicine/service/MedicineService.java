package nuricanozturk.dev.service.medicine.service;

import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import lombok.RequiredArgsConstructor;
import nuricanozturk.dev.service.medicine.dto.MedicineDTO;
import nuricanozturk.dev.service.medicine.dto.MedicinesDTO;
import nuricanozturk.dev.service.medicine.dto.ResponseDTO;
import nuricanozturk.dev.service.medicine.entity.Medicine;
import nuricanozturk.dev.service.medicine.repository.IMedicineRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MedicineService
{
    private final Random m_random;
    private final IMedicineRepository m_medicineRepository;


    public ResponseDTO findMedicinesByNameContainsIgnoreCase(String name, int page, String continuationToken)
    {
        CosmosPageRequest pageable = new CosmosPageRequest(page - 1, 15, continuationToken);

        var result = m_medicineRepository.findByNameContainsIgnoreCase(name, pageable);

        var resultList = result.getContent().parallelStream().map(this::createMedicineDTO).toList();
        var medicinesDTO = new MedicinesDTO(resultList);

        return new ResponseDTO(page, result.getTotalPages(), result.getNumberOfElements(), medicinesDTO);
    }

    private MedicineDTO createMedicineDTO(Medicine medicine)
    {
        double randomPrice = m_random.nextDouble(20, 250);
        var formattedPrice = BigDecimal.valueOf(randomPrice).setScale(2, RoundingMode.HALF_UP);

        return new MedicineDTO(medicine.getName(), medicine.getPrescriptionType(), formattedPrice.doubleValue());
    }
}
