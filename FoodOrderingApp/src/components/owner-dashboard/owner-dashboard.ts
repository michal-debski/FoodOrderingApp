import { Component } from '@angular/core';
import {AddMeal} from './add-meal/add-meal';
import {NgIf} from '@angular/common';
import {OwnerOrders} from './show-orders/owner-orders';
import {AddIngredient} from './add-ingredient/add-ingredient';

@Component({
  selector: 'owner-dashboard',
  imports: [AddMeal, NgIf, OwnerOrders, AddIngredient],
  templateUrl: './owner-dashboard.html',
  styleUrl: './owner-dashboard.css'
})
export class OwnerDashboard {
  selectedView: string = '';

  selectView(view: string) {
    this.selectedView = view;
  }
}
