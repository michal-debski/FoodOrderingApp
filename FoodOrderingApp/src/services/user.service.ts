import { HttpClient } from '@angular/common/http';
import { RegistrationRequest } from '../models/registration.request';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8222/api/v1/auth/registration';
  constructor(private http: HttpClient) {}

  registerUser(registrationRequest: RegistrationRequest) {
    return this.http.post(
      `${this.baseUrl}`,
      registrationRequest,
      { withCredentials: true, responseType: 'text' } 
    );
  }
}
