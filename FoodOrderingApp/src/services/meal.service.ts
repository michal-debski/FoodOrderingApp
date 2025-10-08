import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {MealDTO} from '../models/meal.dto';
import {OrderService} from './order.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {OrderItemDTO} from '../models/order.item.dto';

@Injectable({
    providedIn: 'root'
})
export class MealService {
  meals: MealDTO[] = [];
  private baseUrl = 'http://localhost:8222/api/v1/meals';

  constructor(
    private orderService: OrderService,
    private http: HttpClient
  ) {}
  getMealsByRestaurant(restaurantId: string): Observable<MealDTO[]> {
    return this.http.get<MealDTO[]>(`${this.baseUrl}/${restaurantId}`);
  }


  increase(meal: MealDTO) {
    this.orderService.addMeal(meal);
  }

  decrease(meal: MealDTO) {
    this.orderService.removeMeal(meal);
  }

  getQuantity(meal: MealDTO): number {
    return this.orderService.getQuantity(meal);
  }

  submitOrder() {
    const orderItems: OrderItemDTO[] = this.orderService.getOrderItems();

    if (orderItems.length === 0) {
      alert('Nie dodano żadnych posiłków do zamówienia.');
      return;
    }

  }

  addMeal(meal: MealDTO): Observable<MealDTO> {
    const headers = new HttpHeaders({
      'restaurantId': meal.restaurantId
    });
    console.log("headers: " + headers.get('restaurantId'))

    return this.http.post<MealDTO>(this.baseUrl, meal, { headers });
  }
}
