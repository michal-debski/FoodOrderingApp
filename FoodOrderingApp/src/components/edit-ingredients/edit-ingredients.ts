import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MealDTO } from '../../models/meal.dto';
import { IngredientForMealDTO } from '../../models/meal.ingredient.dto';
import { StorageService } from '../../services/storage.service';
import { DialogService } from '../../services/dialog-service';

@Component({
  selector: 'app-edit-ingredients',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './edit-ingredients.html',
  styleUrl: './edit-ingredients.css',
})
export class EditIngredients implements OnInit {
  @Input() meal?: MealDTO;
  @Input({ required: true }) restaurantId!: string;
  @Output() ingredientsChanged = new EventEmitter<IngredientForMealDTO[]>();
  @Output() close = new EventEmitter<void>();
  ingredientsFromStorage: IngredientForMealDTO[] = [];
  onCancel(): void {
    this.close.emit();
  }

  ingredientsForm!: FormGroup;

  get ingredientsArray(): FormArray {
    return this.ingredientsForm.get('ingredients') as FormArray;
  }

  
  constructor(private fb: FormBuilder, private storageService: StorageService, private dialogService: DialogService) {}

  ngOnInit(): void {
    this.ingredientsForm = this.fb.group({
      selectedIngredient: [''],
      ingredients: this.fb.array([]),
    });
    this.meal?.ingredientsForMeal.forEach((ingredient) => {
      this.ingredientsArray.push(
        this.fb.group({
          name: [ingredient.name],
          unit: [ingredient.unit],
          quantity: [ingredient.quantity, [Validators.required, Validators.min(0.01)]],
        })
      );
    });
    this.loadIngredients();
  }

  loadIngredients() {
    if (!this.restaurantId) return;

    this.storageService.getIngredients(this.restaurantId).subscribe({
      next: (fromStorage) => {
        this.ingredientsFromStorage = fromStorage.ingredients;
      },
      error: (err) => console.error('Error fetching ingredients:', err)
    });
  }

  addIngredient(): void {
    const selected = this.ingredientsForm.get('selectedIngredient')?.value;
    if (!selected) return;

    const alreadyExists = this.ingredientsArray.controls.some(
      (ctrl) => ctrl.get('name')?.value === selected.name
    );
    if (alreadyExists) return;

    this.ingredientsArray.push(
      this.fb.group({
        name: [selected.name],
        unit: [selected.unit],
        quantity: [1, [Validators.required, Validators.min(0.01)]],
      })
    );

    this.ingredientsForm.get('selectedIngredient')?.reset();
  }

  removeIngredient(index: number): void {
    this.ingredientsArray.removeAt(index);
  }

  onSaveIngredients(): void {
    const updatedIngredients = this.ingredientsArray.value as IngredientForMealDTO[];
    console.log(JSON.stringify(updatedIngredients));
    this.ingredientsChanged.emit(updatedIngredients);
  }
  confirmEditIngredients() {
    this.dialogService.openConfirmDialog({
      type: 'edit',
      message: 'Do you confirm to edit ingredients?'
    }).subscribe(confirmed => {
      if (confirmed) {
        this.onSaveIngredients();
      }
    });
  }
}
