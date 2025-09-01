import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {RestaurantService} from '../../services/restaurant-service';
import {RestaurantDTO} from '../../models/restaurant-dto';

@Component({
  selector: 'app-restaurants',
  imports: [
    RouterLinkActive,
    RouterLink
  ],
  templateUrl: './restaurants.component.html',
  styleUrl: './restaurants.component.css'
})
export class RestaurantsComponent {
  restaurants: RestaurantDTO[] = [];

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.restaurantService.restaurants().subscribe({
      next: (data) => {
        this.restaurants = data;
      },
      error: (err) => {
        console.error('Error occurred during getting restaurants', err)
      }
    });
  }

}
