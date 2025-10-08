import { Component } from '@angular/core';
import {DatePipe} from '@angular/common';
import {OrderDTO} from '../../../models/order.dto';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-owner-orders',
  imports: [
    DatePipe
  ],
  templateUrl: './owner-orders.html',
  styleUrl: './owner-orders.css'
})
export class OwnerOrders {
  orders: OrderDTO[] = [];
  availableStatuses: string[] = ['PENDING', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED'];
  editingStatusFor: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const restaurantId = localStorage.getItem('restaurantId');
    if (restaurantId) {
      this.http.get<OrderDTO[]>(`http://localhost:8222/api/v1/orders/${restaurantId}/orders`)
        .subscribe({
          next: (data) => this.orders = data,
          error: (err) => console.error('Failed to fetch orders:', err)
        });
    } else {
      console.warn('No restaurantId in localStorage.');
    }

  }

  cancelOrder(orderNumber: string): void {
    alert(`Mock: cancelling order ${orderNumber}`);
    this.orders = this.orders.map(order =>
      order.orderNumber === orderNumber
        ? { ...order, status: 'CANCELLED', isCancellable: false }
        : order
    );
  }

  changeStatus(orderNumber: string) {
    this.editingStatusFor = this.editingStatusFor === orderNumber ? null : orderNumber;
  }

  updateStatus(order: OrderDTO, newStatus: string) {
    order.status = newStatus;
    this.editingStatusFor = null;

    alert(`Status zamówienia ${order.orderNumber} zmieniony na ${newStatus}`);
  }
}
