package nuricanozturk.dev.service.prescription.controller;

import nuricanozturk.dev.dto.CreatePharmacyDTO;
import nuricanozturk.dev.dto.CreatePrescriptionDTO;
import nuricanozturk.dev.dto.PharmacyDTO;
import nuricanozturk.dev.dto.ResponseDTO;
import nuricanozturk.dev.service.prescription.service.PrescriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prescription")
public class PrescriptionController
{
    private final PrescriptionService m_prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService)
    {
        m_prescriptionService = prescriptionService;
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

    @PostMapping("/create/pharmacy")
    public ResponseDTO createPharmacy(@RequestBody CreatePharmacyDTO createPharmacyDTO)
    {
        try
        {
            return m_prescriptionService.createPharmacy(createPharmacyDTO);
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
    public ResponseDTO findCustomersByIdentityNumberContaining(@RequestParam("id") String identityNumber,
                                                               @RequestParam("page") int page)
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
    public List<PharmacyDTO>  findAllPharmacies()
    {
        return m_prescriptionService.findAllPharmacies();
    }
}
