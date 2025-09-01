import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {Router, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule, RouterLinkActive],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent {
  role: string | null = localStorage.getItem('role');

  constructor(public router: Router) {}

  get isLoginPage(): boolean {
    return this.router.url === '/login';
  }

}
