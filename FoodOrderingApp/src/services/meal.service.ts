import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {MealDTO} from '../models/meal.dto';
import {OrderService} from './order.service';
import {HttpClient} from '@angular/common/http';
import {OrderItemDTO} from '../models/order.item.dto';
import {MealUpdateRequest} from '../models/meal.update.request';

@Injectable({
    providedIn: 'root'
})
export class MealService {
  meals: MealDTO[] = [];
  private baseUrl = 'http://localhost:8222/api/v1/meals';
  constructor(
    private http: HttpClient
  ) {}

  getMealsByRestaurant(restaurantId: string): Observable<MealDTO[]> {
  return this.http.get<MealDTO[]>(`${this.baseUrl}/${restaurantId}`);
}

  addMeal(meal: MealDTO) {
    return this.http.post<MealDTO>(`${this.baseUrl}/${meal.restaurantId}`, meal);
  }

  deleteMeal(name: string) {
    const restaurantId = localStorage.getItem('restaurantId');
    return this.http.delete<MealDTO>(`${this.baseUrl}/${restaurantId}`, {body: name}).subscribe();
  }
  updateMeal(restaurantId: string, mealId: string, meal: MealUpdateRequest) {
    return this.http.put<MealDTO>(`${this.baseUrl}/${restaurantId}/${mealId}`, meal);
  }
}
