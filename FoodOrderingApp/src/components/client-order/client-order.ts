import { Component } from '@angular/core';
import {OrderDTO} from '../../models/order.dto';
import {HttpClient} from '@angular/common/http';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-client-dashboard',
  imports: [
    DatePipe
  ],
  templateUrl: './client-order.html',
  styleUrl: './client-order.css'
})
export class ClientOrder {
  orders: OrderDTO[] = [];


  constructor(private http: HttpClient) {}
  ngOnInit(): void {
      this.http.get<OrderDTO[]>(`http://localhost:8222/api/v1/orders/all`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
          'X-User-Email': `${localStorage.getItem('email')}`
        }
      })
        .subscribe({
          next: (data) => {
            console.log('Orders received:', data);
            this.orders = data;
          },
          error: (err) => console.error('Failed to fetch orders:', err)
        });
  }

  cancelOrder(orderNumber: string): void {
    this.http.delete<OrderDTO>(`http://localhost:8222/api/v1/orders/${orderNumber}`)
      .subscribe({
        error: (err) => console.error('Failed to fetch orders:', err)
      });
  }
}
