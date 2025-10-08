import {Component, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {RestaurantService} from '../../services/restaurant-service';
import {RestaurantDTO} from '../../models/restaurant-dto';

@Component({
  selector: 'app-restaurants',
  imports: [
    RouterLink
  ],
  standalone: true,
  templateUrl: './restaurant.list.component.html',
  styleUrl: './restaurant.list.component.css'
})
export class RestaurantListComponent implements OnInit{
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

}
