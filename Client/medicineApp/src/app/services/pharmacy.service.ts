import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, map, Observable} from "rxjs";
import {getUser} from "./login.service";
import {UserDTO} from "../dto/UserDTO";
import {CreatePrescriptionDTO} from "../dto/CreatePrescriptionDTO";

@Injectable({
  providedIn: 'root'
})
export class PharmacyService {

  constructor(private http: HttpClient) {
  }

  sendSearchCustomer(identityNumber: string): Observable<any> {
    return this.http.get(`https://api-gateway-pharmacy-system.azure-api.net/api/v1/prescription/find/identity?id=${identityNumber}`,
      {headers: {Authorization: "Bearer " + getUser().token}})
      .pipe(
        map((response: any) => {
          if (response.page === 1) {
            return new UserDTO(response.data.fullName, response.data.identityNumber);
          } else {
            return null
          }
        }),
        catchError((error: any | null) => {
            console.log(error);
            return null
          }
        ));
  }


  findMedicines(word: string, page: number): Observable<any> {
    return this.http.get(`https://api-gateway-pharmacy-system.azure-api.net/api/v1/prescription/find/medicine?name=${word}&page=${page}`,
      {headers: {Authorization: "Bearer " + getUser().token}})
      .pipe(
        map((response: any) => {
          if (response.page > 0) {
            console.log(response.data.medicines)
            return response.data.medicines;
          } else {
            return null
          }
        }),
        catchError((error: any | null) => {
            console.log(error);
            return null
          }
        ));
  }


  submitPrescription(prescription: CreatePrescriptionDTO): Observable<any> {
    return this.http.post("https://api-gateway-pharmacy-system.azure-api.net/api/v1/prescription/create", prescription,
      {headers: {Authorization: "Bearer " + getUser().token}})
      .pipe(
        map((response: any) => {
          if (response.page > 0) {
            console.log(response.data)

          } else {
            return null
          }
        }),
        catchError((error: any | null) => {
            console.log(error);
            return null
          }
        ));
  }
}
