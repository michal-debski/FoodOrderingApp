import {Component, Input} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {IngredientForMealDTO} from '../../../models/meal.ingredient.dto';
import {StorageService} from '../../../services/storage.service';
import {DialogService} from '../../../services/dialog-service';
import { Unit } from '../../../models/unit';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-ingredient',
  standalone: true,
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

  unit = Unit;
  units: string[] = Object.values(Unit) as string[]; 
  @Input({ required: true }) restaurantId!: string;

  constructor(private fb: FormBuilder,
              private storageService: StorageService,
              private dialogService: DialogService
  ) {
      this.units = Object.values(this.unit);

  }

  ngOnInit(): void {
    console.log('Restaurant ID:', this.restaurantId);
    this.addIngredientForm = this.fb.group({
      name: ['', Validators.required],
      quantity: [0, Validators.required],
      unit: ['', Validators.required]
    });
  }

  confirmAddIngredient() {
    this.dialogService.openConfirmDialog({
      type: 'add',
      message: 'Do you confirm to add ingredient?'
    }).subscribe(confirmed => {
      if (confirmed) {
        this.addIngredient();
      }
    });
  }

  addIngredient() {
    if (this.addIngredientForm.invalid) {
      this.addIngredientForm.markAllAsTouched();
      return;
    }
    console.log('Restaurant ID:', this.restaurantId);
    const formValues = this.addIngredientForm.value;
    const request: IngredientForMealDTO = {
      name: formValues.name,
      quantity: formValues.quantity,
      unit: formValues.unit
    };
    this.storageService.addIngredient(request, this.restaurantId).subscribe({
      next: (ingredient: any) => {
        console.log('Restaurant ID:', this.restaurantId);
        console.log('Ingredient added successfully:', ingredient);
        console.log('Added ingredient:', ingredient);
        this.addIngredientForm.reset();
        location.reload();

      },
      error: (err: any) => {
        console.error('Error occured during add meal procedure:', err);
      }
    });
  }
}
