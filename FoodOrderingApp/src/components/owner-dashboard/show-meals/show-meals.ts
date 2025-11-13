import { Component, effect, Input, signal } from '@angular/core';
import {DatePipe} from '@angular/common';
import {OrderDTO} from '../../../models/order.dto';
import {HttpClient} from '@angular/common/http';
import {MealDTO} from '../../../models/meal.dto';
import {MealService} from '../../../services/meal.service';
import {DialogService} from '../../../services/dialog-service';
import {EditMeal} from '../edit-meal/edit-meal';

@Component({
  selector: 'app-show-meals',
  imports: [EditMeal],
  templateUrl: './show-meals.html',
  styleUrl: './show-meals.css'
})
export class ShowMeals {

  @Input({ required: true }) restaurantId!: string;

  meals = signal<MealDTO[]>([]);
  selectedView = signal('');
  editingMeal?: MealDTO;

  constructor(
    private http: HttpClient,
    private mealService: MealService,
    private dialogService: DialogService
  ) {}

 ngOnInit(): void {
  if (!this.restaurantId) return;

  this.mealService.getMealsByRestaurant(this.restaurantId).subscribe({
    next: (data) => this.meals.set(data),
    error: (err) => console.error('❌ Error fetching meals', err),
  });
}





  confirmDeleteMeal(name: string) {
    this.dialogService.openConfirmDialog({
      type: 'delete',
      message: 'Do you confirm to delete meal?',
    }).subscribe((confirmed) => {
      if (confirmed) {
        this.mealService.deleteMeal(name);
        this.meals.update((meals) => meals.filter((m) => m.name !== name));
      }
    });
  }

  startEditing(meal: MealDTO) {
    this.editingMeal = { ...meal };
    this.selectedView.set('app-edit-meal');  }
  onMealUpdated(updatedMeal: MealDTO) {
    this.meals.update((meals) =>
      meals.map((m) => (m.name === updatedMeal.name ? updatedMeal : m))
    );
    this.editingMeal = undefined;
    this.selectedView.set('');
  }
}
