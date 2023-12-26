package nuricanozturk.dev.service.prescription.controller;

import nuricanozturk.dev.dto.CreatePrescriptionDTO;
import nuricanozturk.dev.dto.PharmacyDTO;
import nuricanozturk.dev.dto.ResponseDTO;
import nuricanozturk.dev.service.prescription.service.abstraction.IPrescriptionService;
import nuricanozturk.dev.service.prescription.service.abstraction.IPrescriptionServiceV1;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/prescription")
public class PrescriptionControllerV1
{
    private final IPrescriptionServiceV1 m_prescriptionService;

    public PrescriptionControllerV1(@Qualifier("prescriptionServiceV1") IPrescriptionService prescriptionService)
    {
        m_prescriptionService = (IPrescriptionServiceV1) prescriptionService;
    }

    @PostMapping("/create")
    public ResponseDTO createPrescription(@RequestBody CreatePrescriptionDTO createPrescriptionDTO)
    {
        try
        {
            return m_prescriptionService.createPrescription(createPrescriptionDTO);
        } catch (Exception e)
        {
            return new ResponseDTO(-1, -1, -1, e.getMessage());
        }
    }


    @GetMapping("/find/medicine")
    public ResponseDTO getPrescription(@RequestParam("name") String name, @RequestParam("page") int page)
    {
        try
        {
            return m_prescriptionService.getMedicineByName(name, page);
        } catch (Exception e)
        {
            return new ResponseDTO(-1, -1, -1, e.getMessage());
        }
    }

    @GetMapping("/find/identity")
    public ResponseDTO findCustomerByIdentityNumber(@RequestParam("id") String identityNumber)
    {
        try
        {
            return m_prescriptionService.findCustomerByIdentityNumber(identityNumber);
        } catch (Exception e)
        {
            return new ResponseDTO(-1, -1, -1, e.getMessage());
        }
    }

    @GetMapping("/find/identity/containing")
    public ResponseDTO findCustomersByIdentityNumberContaining(@RequestParam("id") String identityNumber, @RequestParam("page") int page)
    {
        try
        {
            return m_prescriptionService.findCustomersByIdentityNumberContaining(identityNumber, page);
        } catch (Exception e)
        {
            return new ResponseDTO(-1, -1, -1, e.getMessage());
        }
    }

    @GetMapping("/find/pharmacies/all")
    public List<PharmacyDTO> findAllPharmacies()
    {
        return m_prescriptionService.findAllPharmacies();
    }
}