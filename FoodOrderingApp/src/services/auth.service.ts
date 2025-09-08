import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {LoginRequest} from '../models/login-request';
import {LoginResponse} from '../models/login-response';


const API_URL = "http://localhost:8222/api/v1/auth/login"
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  login(request: LoginRequest): Observable<LoginResponse> {
    tap({
      next: (res: LoginResponse) => {
        localStorage.setItem('token', res.token!);
        // localStorage.setItem('email', request.email!);
      },
      error: (err) => console.error('Login error', err)
    })
    return this.http.post<LoginResponse>(API_URL, request);
  }
}
