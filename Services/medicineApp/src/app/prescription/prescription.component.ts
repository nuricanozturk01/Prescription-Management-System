import {Component} from '@angular/core';
import {getUser} from "../services/login.service";
import {CreatePrescriptionDTO} from "../dto/CreatePrescriptionDTO";
import {PharmacyService} from "../services/pharmacy.service";
import {MedicineDTO} from "../dto/MedicineDTO";

@Component({
  selector: 'app-prescription',
  templateUrl: './prescription.component.html',
  styleUrls: ['./prescription.component.css']
})
export class PrescriptionComponent {
  public prescriptionDTO = new CreatePrescriptionDTO()

  pharmacyName = getUser().pharmacyName
  searchCustomerId = "10000000000"
  searchMedicineName = ""
  customerPlaceholder = "Please enter the identity Number of the customer then search"

  medicines: MedicineDTO[] = []
  cartMedicines: MedicineDTO[] = []
  page: number = 1
  totalCost: number = 0

  constructor(private pharmacyService: PharmacyService) {
  }

  handleCustomerSearchButton() {
    this.pharmacyService.sendSearchCustomer(this.searchCustomerId).subscribe((result: any) => {
      if (result !== null) {
        this.prescriptionDTO.customerFullName = result.fullName
        this.prescriptionDTO.customerIdentityNumber = result.identityNumber
      } else {
        alert("Customer not found")
      }
    })

  }


  handleMedicineSearchButton() {
    this.pharmacyService.findMedicines(this.searchMedicineName, 1).subscribe((result: any) => {
      if (result !== null) {
        this.medicines = []
        this.page = 1;
        this.medicines = this.medicines.concat(result)
        console.log(this.medicines)
      } else {
        alert("Medicine not found")
      }
    });
  }

  handleMedicineClick(medicine: MedicineDTO) {
    if (!this.cartMedicines.includes(medicine)) {
      this.cartMedicines.push(medicine)
      this.totalCost += medicine.price
    }
  }

  onScroll() {
    console.log('Scrolled');
    this.pharmacyService.findMedicines(this.searchMedicineName, ++this.page).subscribe((result: any) => {
      if (result !== null && result.length > 0) {
        this.medicines = this.medicines.concat(result);
      } else {
        alert("No more medicines found")
      }
    });
  }

  handleRemoveMedicineButton(medicine: MedicineDTO) {
    this.cartMedicines = this.cartMedicines.filter((value => value !== medicine))
    this.totalCost -= medicine.price
    if (this.cartMedicines.length === 0) {
      this.totalCost = 0
    }
  }

  handleSubmitButton() {
    this.prescriptionDTO.medicines = this.cartMedicines
    this.prescriptionDTO.totalPrice = this.totalCost
    this.prescriptionDTO.pharmacyUsername = getUser().pharmacyUsername
    this.pharmacyService.submitPrescription(this.prescriptionDTO).subscribe((result: any) => {
      if (result !== null) {
        this.prescriptionDTO = new CreatePrescriptionDTO()
        this.cartMedicines = []
        this.totalCost = 0
        this.medicines = []
        this.page = 1
        this.searchMedicineName = ""
        this.searchCustomerId = "10000000000"
        alert("Prescription created successfully")
      } else {
        alert("Prescription creation failed")
      }
    });
  }
}
