import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RestaurantDTO} from '../models/restaurant-dto';
import {RestaurantRequest} from '../models/restaurant.request';


const GET_RESTAURANTS_BY_STREET_URL = "http://localhost:8222/api/v1/restaurants/allRestaurants"
const GET_RESTAURANTS = "http://localhost:8222/api/v1/restaurants/allRestaurants"
const ADD_RESTAURANT = "http://localhost:8222/api/v1/restaurants/addRestaurant"
const BASE_URL = "http://localhost:8222/api/v1/restaurants/"

@Injectable({
  providedIn: 'root'
})

export class RestaurantService {
  constructor(private http: HttpClient) {
  }

  getRestaurants(): Observable<RestaurantDTO[]> {
    return this.http.get<RestaurantDTO[]>(`${GET_RESTAURANTS}`);
  }
  restaurants(): Observable<RestaurantDTO[]> {
    const token = localStorage.getItem('token');
    const userEmail = localStorage.getItem('email')
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'X-User-Email': `${userEmail}`
    });

    return this.http.get<RestaurantDTO[]>(GET_RESTAURANTS_BY_STREET_URL,{ headers });
  }

  allRestaurants(): Observable<RestaurantDTO[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<RestaurantDTO[]>(GET_RESTAURANTS,{ headers });
  }

  addRestaurant(request: RestaurantRequest): Observable<RestaurantDTO> {
    return this.http.post<RestaurantDTO>(ADD_RESTAURANT, request);
  }

  deleteRestaurant(restaurantId: string) {
    const concat = BASE_URL.concat(`${restaurantId}`);
    return this.http.delete<RestaurantDTO>(concat);
  }
}
