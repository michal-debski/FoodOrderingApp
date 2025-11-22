import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {Router, RouterLinkActive} from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule, RouterLinkActive],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {
  get role(): string | null {
  return localStorage.getItem('role');
}

  constructor(public router: Router, public authService: AuthService) {}

  get isLoginPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/register';
  }

  logout() {
    console.log("LOGOUT CLICKED");

    localStorage.removeItem('token');
    this.authService.logout().subscribe({
    next: () => {
      console.log("Logged out!");
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      this.router.navigate(['/login']);
    },
    error: (err) => console.error("Logout error", err)
  });
}
    
}

