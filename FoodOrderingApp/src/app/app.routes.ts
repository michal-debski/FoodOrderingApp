import { Routes } from '@angular/router';
import {LoginComponent} from '../components/login/login.component';
import {HomeClientComponent} from '../components/homepage-client/home-client.component';
import {RestaurantsComponent} from '../components/restaurants/restaurants.component';
import {RestaurantAddForm} from '../components/restaurant-add-form/restaurant-add-form';
import {HomeOwnerComponent} from "../components/homepage-owner/home-owner.component";
import {RestaurantListComponent} from '../components/restaurants/restaurant.list.component';
import {Order} from '../components/order/order';
import {OwnerDashboard} from '../components/owner-dashboard/owner-dashboard';
import {ClientOrder} from '../components/client-order/client-order';
import { Registration } from '../components/login/registration/registration';
import { AuthGuard } from '../services/auth.guard';

export const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'home-client', component: HomeClientComponent, canActivate: [AuthGuard]},
  {path: 'home-owner', component: HomeOwnerComponent, canActivate: [AuthGuard]},
  {path: 'restaurants', component: RestaurantsComponent,  canActivate: [AuthGuard]},
  {path: 'restaurants/allRestaurants', component: RestaurantListComponent,  canActivate: [AuthGuard]},
  {path: 'restaurants/addRestaurant', component: RestaurantAddForm,  canActivate: [AuthGuard]},
  {path: 'restaurant/:restaurantId/meals', component: Order, canActivate: [AuthGuard]},
  {path: 'owner/:id', component: OwnerDashboard, canActivate: [AuthGuard]},
  {path: 'orders/all', component: ClientOrder, canActivate: [AuthGuard]},
  {path: 'register', component: Registration}
];
