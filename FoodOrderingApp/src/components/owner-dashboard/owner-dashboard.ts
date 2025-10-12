import {Component} from '@angular/core';
import {AddMeal} from './add-meal/add-meal';
import {OwnerOrders} from './show-orders/owner-orders';
import {AddIngredient} from './add-ingredient/add-ingredient';
import {ShowStorage} from './show-storage/show-storage';

@Component({
  selector: 'owner-dashboard',
  imports: [AddMeal, OwnerOrders, AddIngredient, ShowStorage],
  templateUrl: './owner-dashboard.html',
  styleUrl: './owner-dashboard.css'
})
export class OwnerDashboard {
  selectedView: string = '';

  selectView(view: string) {
    this.selectedView = view;
  }
}
