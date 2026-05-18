import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MealService } from '../../services/meal.service';
import { MealDTO } from '../../models/meal.dto';
import { CartService } from '../../services/cart-service';
import { CommonModule } from '@angular/common';
import { Spinner } from '../shared/spinner/spinner';

@Component({
  selector: 'app-meals',
  imports: [CommonModule, Spinner],
  standalone: true,
  templateUrl: './order.html',
  styleUrl: './order.css',
})
export class Order {
  meals: MealDTO[] = [];
  @Input({ required: true }) restaurantId!: string;

  constructor(
    private mealService: MealService,
    private route: ActivatedRoute,
    private router: Router,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.restaurantId = this.route.snapshot.paramMap.get('restaurantId')!;
    console.log('Restaurant ID:', this.restaurantId);

    this.mealService.getMealsByRestaurant(this.restaurantId).subscribe((meals) => {
      this.meals = meals;
    });
  }

  addToCart(meal: MealDTO) {
    localStorage.setItem('restaurantId', this.restaurantId);
    this.cartService.addMeal(meal.mealId).subscribe({
      next: (response: any) => {
        console.log('Meal added to cart', response);
      },
      error: (err: any) => {
        console.error('Error adding meal to cart', err);
      }
    });
  }

  trackByMealId(index: number, meal: MealDTO) {
    return meal.mealId;
  }
}
