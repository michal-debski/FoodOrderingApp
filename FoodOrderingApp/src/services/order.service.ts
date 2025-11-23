import { Injectable } from '@angular/core';
import { MealDTO } from '../models/meal.dto';
import { OrderItemDTO } from '../models/order.item.dto';
import { OrderDTO } from '../models/order.dto';
import { OrderStatus } from '../models/order.status';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private baseUrl = 'http://localhost:8222/api/v1/orders';
  private items: Map<string, OrderItemDTO> = new Map();

  constructor(private http: HttpClient) {}

  updateOrderStatus(order: OrderDTO, status: OrderStatus) {
    const OrderUpdateStatusRequest = {
      orderNumber: order.orderNumber,
      status: status as 'PREPARATION' | 'DONE' | 'DELIVERED' | unknown,
    };
  console.log("order: " + JSON.stringify(order));
  console.log("orderUpdatestatusrequest: " + JSON.stringify(OrderUpdateStatusRequest))
    return this.http.put<void>(
      `${this.baseUrl}/${order.orderNumber}`,
      OrderUpdateStatusRequest
    ).subscribe({
      next: (res) => console.log('Order status updated', res),
      error: (err) => console.error('Error updating ingredient', err)
    });;
  }

  addMeal(meal: MealDTO) {
    const existing = this.items.get(meal.mealId);
    if (existing) {
      existing.quantity++;
    } else {
      this.items.set(meal.mealId, { mealId: meal.mealId, quantity: 1, unitPrice: meal.price });
    }
    console.log(this.items);
  }

  removeMeal(meal: MealDTO) {
    const existing = this.items.get(meal.mealId);
    if (existing) {
      existing.quantity--;
      if (existing.quantity <= 0) {
        this.items.delete(meal.mealId);
      }
    }
  }

  getQuantity(meal: MealDTO): number {
    return this.items.get(meal.mealId)?.quantity || 0;
  }

  getOrderItems(): OrderItemDTO[] {
    return Array.from(this.items.values());
  }

  clear() {
    this.items.clear();
  }
}
