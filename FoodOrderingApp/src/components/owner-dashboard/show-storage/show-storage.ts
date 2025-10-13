import {Component} from '@angular/core';
import {IngredientForMealDTO} from '../../../models/meal.ingredient.dto';
import {HttpClient} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {StorageService} from '../../../services/storage.service';

@Component({
  selector: 'app-show-storage',
  standalone: true,
  templateUrl: './show-storage.html',
  imports: [FormsModule],
  styleUrl: './show-storage.css'
})
export class ShowStorage {
  ingredients: IngredientForMealDTO[] = [];
  constructor(private http: HttpClient, private storageService: StorageService) {}

  ngOnInit(): void {
    const restaurantId = localStorage.getItem('restaurantId');
    if (restaurantId) {
      this.http.get<{ ingredients: IngredientForMealDTO[] }>(`http://localhost:8222/api/v1/meals/${restaurantId}/storage`)
        .subscribe({
          next: (data) => {
            console.log('Fetched data:', data);
            this.ingredients = data.ingredients
          },
          error: (err) => console.error('Failed to fetch ingredients:', err)
        });
    } else {
      console.warn('No restaurantId in localStorage.');
    }
  }

  changeQuantity(ingredient: IngredientForMealDTO) {
    const restaurantId = localStorage.getItem('restaurantId');
    if (!restaurantId) {
      console.error('No restaurantId found in localStorage');
      return;
    }
    console.log();

    const updatedIngredient = {
      name: ingredient.name,
      quantity: ingredient.quantity,
      unitName: ingredient.unitName
    };

    this.http.put(
      `http://localhost:8222/api/v1/meals/${restaurantId}/storage`,
      updatedIngredient
    ).subscribe({
      next: (res) => console.log('Ingredient updated', res),
      error: (err) => console.error('Error updating ingredient', err)
    });
  }

  deleteIngredient(ingredient: IngredientForMealDTO) {
    const restaurantId = localStorage.getItem('restaurantId');
    if (!restaurantId) {
      console.error('No restaurantId found in localStorage');
      return;
    }
    return this.storageService.deleteIngredient(ingredient, restaurantId);
  }
}
