import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MealDTO} from '../../../models/meal.dto';
import {MealService} from '../../../services/meal.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-add-meal',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './add-meal.html',
  styleUrl: './add-meal.css'
})
export class AddMeal {

  addMealForm!: FormGroup;
  constructor(private fb: FormBuilder, private mealService: MealService) {}

  ngOnInit(): void {
    this.addMealForm = this.fb.group({
      name: ['', Validators.required],
      category: ['', Validators.required],
      description: [''],
      price: [0, [Validators.required, Validators.min(0.01)]]
    });
  }

  addMeal() {
    if (this.addMealForm.invalid) {
      this.addMealForm.markAllAsTouched();
      return;
    }

    const restaurantId = localStorage.getItem('restaurantId');
    const request: MealDTO = { ...this.addMealForm.value, restaurantId };

    this.mealService.addMeal(request).subscribe({
      next: (meal: any) => {
        console.log('Added meal:', meal);
        this.addMealForm.reset();
      },
      error: (err: any) => {
        console.error('Error occured during add meal procedure:', err);
      }
    });
  }

}
