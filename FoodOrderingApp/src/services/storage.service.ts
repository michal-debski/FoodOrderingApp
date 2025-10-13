import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {IngredientForMealDTO} from '../models/meal.ingredient.dto';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(private http: HttpClient) {}

  addIngredient(ingredient: IngredientForMealDTO): Observable<IngredientForMealDTO> {
    const restaurantId = localStorage.getItem('restaurantId');
    const url = `http://localhost:8222/api/v1/meals/${restaurantId}/storage`;

    return this.http.post<IngredientForMealDTO>(url, ingredient);
  }

  getIngredients(restaurantId: string) {
    return this.http.get<{ ingredients: IngredientForMealDTO[] }>(
      `http://localhost:8222/api/v1/meals/${restaurantId}/storage`
    );
  }

  deleteIngredient(ingredient: IngredientForMealDTO, restaurantId:string) {
    const url = `http://localhost:8222/api/v1/meals/${restaurantId}/storage`;
    console.log(url)
    console.log(ingredient)
    return this.http.delete<void>(url, {body: ingredient}).subscribe();

  }
}
