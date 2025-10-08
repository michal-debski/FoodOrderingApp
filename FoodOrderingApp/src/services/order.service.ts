import {Injectable} from '@angular/core';
import {MealDTO} from '../models/meal.dto';
import {OrderItemDTO} from '../models/order.item.dto';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private items: Map<string, OrderItemDTO> = new Map();

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
