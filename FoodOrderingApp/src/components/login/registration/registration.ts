import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DialogService } from '../../../services/dialog-service';
import { RegistrationRequest } from '../../../models/registration.request';
import { UserService } from '../../../services/user.service';
import { Role } from '../../../models/role';
import { Router, RouterLink } from '@angular/router';
import { HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-registration',
  imports: [FormsModule, ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './registration.html',
  styleUrl: './registration.css',
})
export class Registration {
  registrationForm!: FormGroup;
  role = Role;
  roleLabels: { [key: string]: string } = {
    [Role.OWNER]: 'RESTAURANT OWNER',
    [Role.USER]: 'USER',
  };
  roles: string[] = Object.values(Role);

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private dialogService: DialogService,
    private router: Router
  ) {
    this.roles = Object.values(this.role);
  }

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
      role: ['', Validators.required],
    });
  }
  confirmationRegisterNewUser() {
    this.dialogService
      .openConfirmDialog({
        type: 'add',
        message: 'Do you confirm to register user?',
      })
      .subscribe((confirmed) => {
        if (confirmed) {
          this.registerUser();
        }
      });
  }

  registerUser() {
    if (this.registrationForm.invalid) {
      this.registrationForm.markAllAsTouched();
      return;
    }

    const request: RegistrationRequest = {
      ...this.registrationForm.value,
    };

    this.userService.registerUser(request).subscribe({
      next: (registration: any) => {
        console.log('Added user:', registration);
        this.registrationForm.reset();
        alert('You have been registered successfully!');
        this.router.navigate(['/login']);
      },
      error: (err: any) => {
        if (err.status === 409) {
          alert('User with this email already exists. \n Go to login page or try with other email :)');
        }
        console.error('Error occured during register new user procedure:', err);
      },
    });
  }
}
