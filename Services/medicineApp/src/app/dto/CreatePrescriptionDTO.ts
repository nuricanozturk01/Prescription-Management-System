import {MedicineDTO} from "./MedicineDTO";
import {getUser} from "../services/login.service";

export class CreatePrescriptionDTO {
  public pharmacyName: string;
  public pharmacyUsername: string;
  public customerIdentityNumber: string;
  public customerFullName: string;
  public totalPrice: number;
  public medicines: MedicineDTO[]

  constructor() {
    const user = getUser()
    this.pharmacyName = user.pharmacyName
    this.pharmacyUsername = user.username
  }
}
