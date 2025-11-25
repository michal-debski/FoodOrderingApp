import { Component } from '@angular/core';
import { OrderDTO } from '../../models/order.dto';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { OrderWithMealDataResponse } from '../../models/order.with.meal.data.response';
import { DialogService } from '../../services/dialog-service';

@Component({
  selector: 'app-client-dashboard',
  imports: [DatePipe],
  templateUrl: './client-order.html',
  styleUrl: './client-order.css',
})
export class ClientOrder {
  orders: OrderWithMealDataResponse[] = [];
  constructor(private http: HttpClient, private dialogService: DialogService) {}

  ngOnInit(): void {
    console.log(localStorage.getItem('email'));
    this.http
      .get<OrderWithMealDataResponse[]>(`http://localhost:8222/api/v1/orders/all`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
          'X-User-Email': `${localStorage.getItem('email')}`,
        },
      })
      .subscribe({
        next: (data) => {
          console.log('Orders received:', data);
          this.orders = data;
        },
        error: (err) => console.error('Failed to fetch orders:', err),
      });
  }

  confirmFinishOrder(orderNumber: string) {
    this.dialogService
      .openConfirmDialog({
        type: 'cancel',
        message: 'Do you confirm to cancel order?',
      })
      .subscribe((confirmed) => {
        if (confirmed) {
          this.cancelOrder(orderNumber);
        }
      });
  }

  cancelOrder(orderNumber: string): void {
    this.http.delete<OrderDTO>(`http://localhost:8222/api/v1/orders/${orderNumber}`).subscribe({
      error: (err) => console.error('Failed to fetch orders:', err),
    });
  }
}
