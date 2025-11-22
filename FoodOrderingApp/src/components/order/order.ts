import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MealService } from '../../services/meal.service';
import { MealDTO } from '../../models/meal.dto';
import { OrderService } from '../../services/order.service';
import { OrderItemDTO } from '../../models/order.item.dto';
import { HttpClient } from '@angular/common/http';
import { OrderRequestDto } from '../../models/order.request.dto';
import { Spinner } from '../shared/spinner/spinner';
import { firstValueFrom, timer } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-meals',
  imports: [CommonModule, Spinner],
  standalone: true,
  templateUrl: './order.html',
  styleUrl: './order.css',
})
export class Order {
  @Output() quantityChanged = new EventEmitter<{ mealId: string; quantity: number }>();
  meals: MealDTO[] = [];
  @Input({ required: true }) restaurantId!: string;

  isPreparing = false;

  constructor(
    private mealService: MealService,
    private route: ActivatedRoute,
    private router: Router,
    private orderService: OrderService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.restaurantId = this.route.snapshot.paramMap.get('restaurantId')!;
    console.log('Restaurant ID:', this.restaurantId);

    this.mealService.getMealsByRestaurant(this.restaurantId).subscribe((meals) => {
      this.meals = meals;
    });
  }

  increase(meal: MealDTO) {
    this.orderService.addMeal(meal);
    this.quantityChanged.emit({
      mealId: meal.mealId,
      quantity: this.orderService.getQuantity(meal),
    });
  }

  decrease(meal: MealDTO) {
    this.orderService.removeMeal(meal);
    this.quantityChanged.emit({
      mealId: meal.mealId,
      quantity: this.orderService.getQuantity(meal),
    });
  }

  getQuantity(meal: MealDTO): number {
    return this.orderService.getQuantity(meal);
  }

  trackByMealId(index: number, meal: MealDTO) {
    return meal.mealId;
  }
  async finishOrder() {
    console.log('Restaurant ID:', this.restaurantId);

    const orderItems: OrderItemDTO[] = this.orderService.getOrderItems();
    if (!orderItems.length) {
      alert('Cart is empty!');
      return;
    }
    console.log('Sending order:', orderItems);
    const requestBody = { orderItems };
    this.isPreparing = true;
    try {
      await firstValueFrom(
        this.http.post<OrderRequestDto>(
          `http://localhost:8222/api/v1/orders/${this.restaurantId}/order`,
          requestBody,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('token')}`,
              'X-User-Email': 'user@example.com',
            },
          }
        )
      );
      await new Promise((resolve) => setTimeout(resolve, 5000));

      this.isPreparing = false;
      this.orderService.clear();
      ('Successfully performed order!');
      this.router.navigate(['/restaurants/all-restaurants']);
    } catch (err: any) {
      this.isPreparing = false;
      if (err.status === 409 && err.error?.unavailableMeals) {
        const missing = err.error.unavailableMeals.join(', ');
        alert(`Cannot prepare meals: ${missing}, missing ingredients`);
      } else {
        alert('Error occurred while performing order.');
      }
    }
  }
}
