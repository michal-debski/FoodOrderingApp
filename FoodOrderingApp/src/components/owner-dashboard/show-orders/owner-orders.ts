import { Component, Input } from '@angular/core';
import { DatePipe, NgIf } from '@angular/common';
import { OrderDTO } from '../../../models/order.dto';
import { HttpClient } from '@angular/common/http';
import { OrderWithMealDataResponse } from '../../../models/order.with.meal.data.response';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { OrderStatus } from '../../../models/order.status';
import { OrderService } from '../../../services/order.service';

@Component({
  selector: 'app-owner-orders',
  imports: [DatePipe, MatFormFieldModule, MatSelectModule],
  templateUrl: './owner-orders.html',
  styleUrl: './owner-orders.css',
})
export class OwnerOrders {
  @Input({ required: true }) restaurantId!: string;
  orders: OrderWithMealDataResponse[] = [];
  editingStatusFor: string | null = null;
  orderStatus = OrderStatus;
  selectedStatus?: OrderStatus;

  orderStatuses: string[] = Object.values(this.orderStatus);
  constructor(private http: HttpClient, private orderService: OrderService) {}

  ngOnInit(): void {
    if (this.restaurantId) {
      this.http
        .get<OrderWithMealDataResponse[]>(
          `http://localhost:8222/api/v1/orders/${this.restaurantId}/orders`
        )
        .subscribe({
          next: (data) => (this.orders = data),
          error: (err) => console.error('Failed to fetch orders:', err),
        });
    } else {
      console.warn('No restaurantId found');
    }
  }

  cancelOrder(orderNumber: string): void {
    alert(`Mock: cancelling order ${orderNumber}`);
    this.orders = this.orders.map((order) =>
      order.orderNumber === orderNumber
        ? { ...order, status: 'CANCELLED', isCancellable: false }
        : order
    );
  }

  changeStatus(orderNumber: string) {
    this.editingStatusFor = this.editingStatusFor === orderNumber ? null : orderNumber;
  }

  updateStatus(order: OrderDTO, newStatus: OrderStatus) {
    if (order.status === newStatus) {
      alert(`Order status has selected status, please select other type`);
    } else {
      this.orderService.updateOrderStatus(order, newStatus);
      alert(`Order status changed for  ${order.orderNumber} to ${newStatus}`);
    }
  }
}
