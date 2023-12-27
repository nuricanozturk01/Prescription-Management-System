import {Component} from '@angular/core';
import {getUser} from "../services/login.service";

@Component({
  selector: 'app-prescription',
  templateUrl: './prescription.component.html',
  styleUrls: ['./prescription.component.css']
})
export class PrescriptionComponent {
  pharmacyName = getUser().pharmacyName
  customerPlaceholder = "Please enter the identity Number of the customer then search"
  customer = ""
}
