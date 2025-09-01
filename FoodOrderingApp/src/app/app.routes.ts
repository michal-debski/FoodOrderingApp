import { Routes } from '@angular/router';
import {LoginComponent} from '../components/login/login.component';
import {HomeClientComponent} from '../components/homepage-client/home-client.component';
import {RestaurantsComponent} from '../components/restaurants/restaurants.component';
import {RestaurantAddForm} from '../components/restaurant-add-form/restaurant-add-form';

export const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'home-client', component: HomeClientComponent },
  {path: 'restaurants', component: RestaurantsComponent},
  {path: 'restaurants/addRestaurant', component: RestaurantAddForm}
];
