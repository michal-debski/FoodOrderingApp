import {Component} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MealDTO} from '../../../models/meal.dto';
import {MealService} from '../../../services/meal.service';
import {CommonModule} from '@angular/common';
import {StorageService} from '../../../services/storage.service';
import {IngredientForMealDTO} from '../../../models/meal.ingredient.dto';
import {DialogService} from '../../../services/dialog-service';

@Component({
  selector: 'app-add-meal',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './add-meal.html',
  styleUrl: './add-meal.css'
})
export class AddMeal {
  ingredients: IngredientForMealDTO[] = [];
  selectedIngredient?: IngredientForMealDTO;

  addMealForm!: FormGroup;
  constructor(private fb: FormBuilder,
              private mealService: MealService,
              private storageService: StorageService,
              private dialogService: DialogService
  ) {}

  ngOnInit(): void {
    this.addMealForm = this.fb.group({
      name: ['', Validators.required],
      category: ['', Validators.required],
      description: [''],
      price: [0, [Validators.required, Validators.min(0.01)]],
      ingredients: this.fb.array([]),
      selectedIngredient: [null],

    });
    this.loadIngredients();
  }

  get ingredientsArray(): FormArray {
    return this.addMealForm.get('ingredients') as FormArray;
  }

  loadIngredients() {
    const restaurantId = localStorage.getItem('restaurantId');
    if (!restaurantId) return;

    this.storageService.getIngredients(restaurantId).subscribe({
      next: (data) => {
        this.ingredients = data.ingredients;
      },
      error: (err) => console.error('Error fetching ingredients:', err)
    });
  }

  confirmAddMeal() {
    this.dialogService.openConfirmDialog({
      type: 'add',
      message: 'Do you confirm to add meal?'
    }).subscribe(confirmed => {
      if (confirmed) {
        this.addMeal();
      }
    });
  }

  addMeal() {
    if (this.addMealForm.invalid) {
      this.addMealForm.markAllAsTouched();
      return;
    }

    const restaurantId = localStorage.getItem('restaurantId');
    const request: MealDTO = { ...this.addMealForm.value, restaurantId, ingredientsForMeal: this.addMealForm.value.ingredients };

    this.mealService.addMeal(request).subscribe({
      next: (meal: any) => {
        console.log('Added meal:', meal);
        this.addMealForm.reset();
        this.ingredientsArray.clear();
        location.reload();
      },
      error: (err: any) => {
        console.error('Error occured during add meal procedure:', err);
      }
    });
  }

  addIngredient() {
    const ingredient = this.addMealForm.get('selectedIngredient')?.value;
    if (!ingredient) return;

    const alreadyExists = this.ingredientsArray.value.some(
      (i: any) => i.name === ingredient.name
    );
    if (alreadyExists) return;

    const ingredientGroup = this.fb.group({
      name: [ingredient.name],
      unit: [ingredient.unitName],
      quantity: [1, [Validators.required, Validators.min(0.01)]],
    });

    this.ingredientsArray.push(ingredientGroup);
    this.addMealForm.get('selectedIngredient')?.reset();
  }

  removeIngredient(index: number) {
    this.ingredientsArray.removeAt(index);
  }

}
