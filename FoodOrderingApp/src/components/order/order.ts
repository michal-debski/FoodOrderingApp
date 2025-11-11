import {Component, EventEmitter, Output} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MealService} from '../../services/meal.service';
import {MealDTO} from '../../models/meal.dto';
import {OrderService} from '../../services/order.service';
import {OrderItemDTO} from '../../models/order.item.dto';
import {HttpClient} from '@angular/common/http';
import {OrderRequestDto} from '../../models/order.request.dto';

@Component({
  selector: 'app-meals',
  imports: [],
  standalone: true,
  templateUrl: './order.html',
  styleUrl: './order.css'
})
export class Order {
  @Output() quantityChanged = new EventEmitter<{ mealId: string, quantity: number }>();
  meals: MealDTO[] = [];
  restaurantId!: string;

  constructor(private mealService: MealService,
              private route: ActivatedRoute,
              private router: Router,
              private orderService: OrderService,
              private http: HttpClient,

  ) {}

  ngOnInit(): void {
    this.restaurantId = this.route.snapshot.paramMap.get('id')!;
    console.log('Restaurant ID:', this.restaurantId);

    this.mealService.getMealsByRestaurant(this.restaurantId).subscribe(meals =>{
      this.meals = meals;
      console.log('Fetched meals:', this.meals);
    })
  }

  increase(meal: MealDTO) {
    this.orderService.addMeal(meal);
    this.quantityChanged.emit({ mealId: meal.mealId, quantity: this.orderService.getQuantity(meal) });
  }

  decrease(meal: MealDTO) {
    this.orderService.removeMeal(meal);
    this.quantityChanged.emit({ mealId: meal.mealId, quantity: this.orderService.getQuantity(meal) });
  }

  getQuantity(meal: MealDTO): number {
    return this.orderService.getQuantity(meal);
  }

  trackByMealId(index: number, meal: MealDTO) {
    return meal.mealId;
  }
  finishOrder() {
    this.restaurantId = this.route.snapshot.paramMap.get('id')!;
    console.log('Restaurant ID:', this.restaurantId);

    const orderItems: OrderItemDTO[] = this.orderService.getOrderItems();
    if (!orderItems.length) {
      alert('Cart is empty!');
      return;
    }
    console.log('Sending order:', orderItems); // Add this line

    const requestBody = {orderItems};
    this.http.post<OrderRequestDto>(`http://localhost:8222/api/v1/orders/${this.restaurantId}/order`, requestBody, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'X-User-Email': 'user@example.com'
      }
    }).subscribe({
      next: (order) => {
        this.orderService.clear();
        alert('Successfully performed order!');
        this.router.navigate(['/restaurants/all-restaurants']);
      },
      error: (err) => {
        if (err.status === 409 && err.error?.unavailableMeals) {
          const missing = err.error.unavailableMeals.join(', ');
          alert(`Cannot prepare meals: ${missing}, missing ingredients`);
        } else {
          alert('Error occurred while performing order.');
        }
      }
    });
  }
}
