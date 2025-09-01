import { Component } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {LoginRequest} from '../../models/login-request';
import {Router} from '@angular/router';
import {tap} from 'rxjs';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(
    private authorization: AuthService,
    private router: Router
    ) {}

  userForm : FormGroup = new FormGroup({
      email: new FormControl(''),
      password: new FormControl('')
    });

  request: LoginRequest = new LoginRequest();

  login() {
    const formValue = this.userForm.value;
    console.log("Login function called");

    if (formValue.email == '' || formValue.password == '') {
      alert('Wrong credentials');
      return;
    }

    this.request.email = formValue.email;
    this.request.password = formValue.password;

    this.authorization.login(this.request).pipe(
      tap({
        next: (res: any) => {
          const role = res.role;
          localStorage.setItem('role', res.role);
          console.log("Role:" + res.role);
          if (role === 'RESTAURANT_OWNER') {
            console.log("Received Response:" + res.token);
            this.router.navigate(['/home-owner']);
          } else if (role === 'USER') {
            console.log("Received Response:" + res.token);
            this.router.navigate(['/home-client']);
          } else if (role === 'ADMIN') {
            console.log("Received Response:" + res.token);
            this.router.navigate(['/home-admin']);
          }
        },
        error: (err) => {
          console.error("Error Received Response: ", err);
        }
      })
    ).subscribe();
  }
}
