import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {getUser, LoginService} from "../services/login.service";
import {NgForm} from "@angular/forms";
import {LoginDTO} from "../dto/LoginDTO";
import {CURRENT_USER} from "../Constants";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginModel = new LoginDTO()

  ngOnInit() {
    localStorage.removeItem(CURRENT_USER);
  }

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
