import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {getUser, LoginService} from "../services/login.service";
import {NgForm} from "@angular/forms";
import {LoginDTO} from "../dto/LoginDTO";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginModel = new LoginDTO()

  constructor(private router: Router, private loginService: LoginService) {
  }

  handleLoginButton(loginForm: NgForm) {
    this.loginService.sendLoginRequest(this.loginModel).subscribe((result: boolean) => {
      if (result) {
        this.router.navigate(['/prescription']).then(r => console.log('Giriş başarılı'));
      } else {
        console.log('Giriş başarısız');
      }
    })
  }
}
