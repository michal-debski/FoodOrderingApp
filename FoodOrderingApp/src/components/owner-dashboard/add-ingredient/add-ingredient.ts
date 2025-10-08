import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {IngredientForMealDTO} from '../../../models/meal.ingredient.dto';
import {StorageService} from '../../../services/storage.service';

@Component({
  selector: 'app-add-ingredient',
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './add-ingredient.html',
  styleUrl: './add-ingredient.css'
})
export class AddIngredient {
  addIngredientForm!: FormGroup;
  constructor(private fb: FormBuilder, private storageService: StorageService) {}

  ngOnInit(): void {
    this.addIngredientForm = this.fb.group({
      name: ['', Validators.required],
      quantity: [0, Validators.required],
      unitName: ['', Validators.required]
    });
  }

  addIngredient() {
    if (this.addIngredientForm.invalid) {
      this.addIngredientForm.markAllAsTouched();
      return;
    }

    const restaurantId = localStorage.getItem('restaurantId');
    const request: IngredientForMealDTO = { ...this.addIngredientForm.value, restaurantId };

    this.storageService.addIngredient(request).subscribe({
      next: (ingredient: any) => {
        console.log('Added ingredient:', ingredient);
        this.addIngredientForm.reset();
      },
      error: (err: any) => {
        console.error('Error occured during add meal procedure:', err);
      }
    });
  }
}
