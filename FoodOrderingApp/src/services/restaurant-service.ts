import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import {RestaurantDTO} from '../models/restaurant-dto';
import {MOCK_RESTAURANTS} from '../assets/restaurant.mock';
import {RestaurantRequest} from '../models/restaurant.request';


const GET_RESTAURANTS_BY_STREET_URL = "http://localhost:8222/api/v1/restaurants/available"
const ADD_RESTAURANT = "http://localhost:8222/api/v1/restaurants/addRestaurant"

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {
  constructor(private http: HttpClient) {
  }

  getRestaurants(): Observable<RestaurantDTO[]> {
    return of(MOCK_RESTAURANTS);
  }
  restaurants(): Observable<RestaurantDTO[]> {
    return this.http.get<RestaurantDTO[]>(GET_RESTAURANTS_BY_STREET_URL);
  }
  addRestaurant(request: RestaurantRequest): Observable<RestaurantDTO> {
    return this.http.post<RestaurantDTO>(ADD_RESTAURANT, request);
  }
}
