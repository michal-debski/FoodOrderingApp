import {Observable, of} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {IngredientForMealDTO} from '../models/meal.ingredient.dto';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  ingredientsForMeal: IngredientForMealDTO[] = [];


  ngOnInit() {
    this.ingredientsForMeal = [
      {
        name: 'Tomato',
        quantity: 2,
        unitName: 'pcs'
      },
      {
        name: 'Cheese',
        quantity: 100,
        unitName: 'g'
      },
      {
        name: 'Olive Oil',
        quantity: 2,
        unitName: 'tbsp'
      },
      {
        name: 'Lettuce',
        quantity: 1,
        unitName: 'head'
      },
      {
        name: 'Beef Patty',
        quantity: 1,
        unitName: 'pcs'
      }
    ];
  }
  addIngredient(ingredient: IngredientForMealDTO): Observable<IngredientForMealDTO> {
    const restaurantId = localStorage.getItem('restaurantId');
    const headers = new HttpHeaders({
      'restaurantId': `${restaurantId}`
    });
    console.log("headers: " + headers.get('restaurantId'))
    return of(ingredient);

    // return this.http.post<MealDTO>(this.baseUrl, ingredient, { headers });
  }
}
