import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginDTO} from "../dto/LoginDTO";
import {catchError, map, Observable} from "rxjs";
import {CURRENT_USER} from "../Constants";

export const getUser = () => JSON.parse(localStorage.getItem(CURRENT_USER));

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {
  }

  sendLoginRequest(loginModel: LoginDTO): Observable<boolean> {

    return this.http.post('http://localhost:4041/api/auth/login', loginModel)
      .pipe(
        map((response: any) => {
          if (response.page === -1) {
            localStorage.clear();
            return false;
          } else {
            localStorage.setItem(CURRENT_USER, JSON.stringify(response.data));
            return true;
          }
        }),
        catchError((error: any) => {
            console.log(error);
            return [false];
          }
        ));
  }
}
