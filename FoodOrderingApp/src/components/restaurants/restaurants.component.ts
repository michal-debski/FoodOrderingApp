import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {RestaurantService} from '../../services/restaurant-service';
import {RestaurantDTO} from '../../models/restaurant-dto';

@Component({
  selector: 'app-restaurants',
  imports: [
    RouterLinkActive,
    RouterLink
  ],
  standalone: true,
  templateUrl: './restaurants.component.html',
  styleUrl: './restaurants.component.css'
})
export class RestaurantsComponent implements OnInit{
  restaurants: RestaurantDTO[] = [];

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.restaurantService.getRestaurants().subscribe({
      next: (data) => {
        this.restaurants = data;
      },
      error: (err) => {
        console.error('Error occurred during getting restaurants', err)
      }
    });
  }

  selectRestaurant(restaurantId: string) {
    localStorage.setItem('restaurantId', restaurantId);
    console.log('Selected restaurantId:', restaurantId);
  }

  deleteRestaurant(restaurantId: string) {
    console.log("Deleting")
    this.restaurantService.deleteRestaurant(restaurantId).subscribe();
  }
}
