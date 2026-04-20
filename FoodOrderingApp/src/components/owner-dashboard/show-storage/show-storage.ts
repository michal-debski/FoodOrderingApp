import {Component, Input} from '@angular/core';
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
  @Input({ required: true }) restaurantId!: string;
  ingredients: IngredientForMealDTO[] = [];
  constructor(private http: HttpClient, private storageService: StorageService) {}

  ngOnInit(): void {
    if (this.restaurantId) {
        this.fetchIngredients();
    } else {
      console.warn('No restaurantId found');
    }
  }

  fetchIngredients(): void {
    this.http.get<{ ingredients: IngredientForMealDTO[] }>(`http://localhost:8222/api/v1/meals/${this.restaurantId}/storage`)
            .subscribe({
              next: (data) => {
                console.log('Fetched data:', data);
                this.ingredients = data.ingredients
              },
              error: (err) => console.error('Failed to fetch ingredients:', err)
            });
  }

  changeQuantity(ingredient: IngredientForMealDTO) {
    if (!this.restaurantId) {
      console.error('No restaurantId found');
      return;
    }
    console.log();

    const updatedIngredient = {
      name: ingredient.name,
      quantity: ingredient.quantity,
      unitName: ingredient.unit
    };

    this.http.put(
      `http://localhost:8222/api/v1/meals/${this.restaurantId}/storage`,
      updatedIngredient
    ).subscribe({
      next: (res) => console.log('Ingredient updated', res),
      error: (err) => console.error('Error updating ingredient', err)
    });
  }

  deleteIngredient(ingredient: IngredientForMealDTO) {
    if (!this.restaurantId) {
      console.error('No restaurantId');
      return;
    }
    this.storageService.deleteIngredient(ingredient, this.restaurantId)
             .subscribe({
               next: () => {
                 this.fetchIngredients();
               }
             });
  }
}
