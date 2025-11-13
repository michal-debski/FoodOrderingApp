import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgIf } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IngredientForMealDTO } from '../../../models/meal.ingredient.dto';
import { MealService } from '../../../services/meal.service';
import { StorageService } from '../../../services/storage.service';
import { DialogService } from '../../../services/dialog-service';
import { MealDTO } from '../../../models/meal.dto';
import { MealUpdateRequest } from '../../../models/meal.update.request';
import { EditIngredients } from '../../edit-ingredients/edit-ingredients';


@Component({
  selector: 'app-edit-meal',
  standalone: true,
  imports: [NgIf, ReactiveFormsModule, EditIngredients],
  templateUrl: './edit-meal.html',
  styleUrl: './edit-meal.css',
})
export class EditMeal implements OnInit{
  @Input() meal?: MealDTO;
  @Output() mealUpdated = new EventEmitter<MealDTO>();
  @Input({ required: true }) restaurantId!: string;

  ingredients: IngredientForMealDTO[] = [];
  editMealForm!: FormGroup;
  originalIngredients: IngredientForMealDTO[] = [];
  updatedIngredients: IngredientForMealDTO[] = [];

  constructor(
    private fb: FormBuilder,
    private mealService: MealService,
    private dialogService: DialogService
  ) {}

  ngOnInit(): void {
    this.originalIngredients = this.meal?.ingredientsForMeal ?? [];
    this.updatedIngredients = [...this.originalIngredients];
    this.editMealForm = this.fb.group({
      name: [this.meal?.name, Validators.required],
      category: [this.meal?.category],
      description: [this.meal?.description],
      price: [this.meal?.price, [Validators.required, Validators.min(0.01)]],
      ingredients: this.fb.array(this.meal?.ingredientsForMeal!),
    });
    this.editMealForm.get('category')?.disable();
  }

  onSubmit(): void {
    const hasIngredientChanges =
      JSON.stringify(this.updatedIngredients) !== JSON.stringify(this.originalIngredients);

    this.dialogService
      .openConfirmDialog({
        type: 'edit',
        message: 'Do you confirm to edit meal?',
      })
      .subscribe((confirmed) => {
        if (confirmed) {
          let price = this.editMealForm.get('price')?.value;
          const updatedMealFromForm: MealUpdateRequest = {
            name: this.editMealForm.get('name')?.value,
            price: price,
            description: this.editMealForm.get('description')?.value,
            ingredientsForMeal: hasIngredientChanges
              ? this.updatedIngredients
              : this.originalIngredients,
          };
          console.log('REQUEST --> ' + JSON.stringify(updatedMealFromForm));
          this.mealService
            .updateMeal(this.restaurantId!, this.meal?.mealId!, updatedMealFromForm)
            .subscribe({
              next: (updatedMeal) => {
                this.mealUpdated.emit(updatedMeal);
                console.log('✅ Meal updated');
              },
              error: (err) => console.error('❌ Error updating meal', err),
            });
        }
      });
  }
  onIngredientsChanged(newIngredients: IngredientForMealDTO[]): void {
    this.updatedIngredients = newIngredients;
  }
  showIngredientsModal = false;

openIngredientsModal(): void {
  this.showIngredientsModal = true;
}

closeIngredientsModal(): void {
  this.showIngredientsModal = false;
}

}
