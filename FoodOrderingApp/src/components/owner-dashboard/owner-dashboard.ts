import {Component, OnInit} from '@angular/core';
import {AddMeal} from './add-meal/add-meal';
import {OwnerOrders} from './show-orders/owner-orders';
import {AddIngredient} from './add-ingredient/add-ingredient';
import {ShowStorage} from './show-storage/show-storage';
import {ShowMeals} from './show-meals/show-meals';
import { ActivatedRoute } from '@angular/router';
import { EditMeal } from './edit-meal/edit-meal';

@Component({
  selector: 'owner-dashboard',
  standalone: true,
  imports: [AddMeal, OwnerOrders, AddIngredient, ShowStorage, ShowMeals],
  templateUrl: './owner-dashboard.html',
  styleUrl: './owner-dashboard.css'
})
export class OwnerDashboard implements OnInit {
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.selectedRestaurantId = this.route.snapshot.paramMap.get('id') ?? '';
    console.log('selectedRestaurantId:', this.selectedRestaurantId);
  }
  
  selectedView: string = 'show-meals';
  selectedRestaurantId?: string;

  selectView(view: string) {
    this.selectedView = view;
  }
  onRestaurantSelected(id: string) {
    this.selectedRestaurantId = id;
  }
}
