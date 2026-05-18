import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ShoppingCartResponse } from "../models/shopping.cart.response";

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private baseUrl = 'http://localhost:8222/api/v1/cart';

  constructor(private http: HttpClient) {}

  private getHeaders() {
    return {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
      'X-User-Email': `${localStorage.getItem('email')}`,
    };
  }

  getCart(): Observable<ShoppingCartResponse> {
    return this.http
      .get<ShoppingCartResponse>(`${this.baseUrl}`, {
        headers: this.getHeaders(),
      });
  }

  addMeal(mealId: string): Observable<ShoppingCartResponse> {
    const request = { mealId };
    return this.http.post<ShoppingCartResponse>(`${this.baseUrl}`, request, {
      headers: this.getHeaders(),
    });
  }

  removeMeal(mealId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/meals/${mealId}`, { headers: this.getHeaders()});
  }

  clearCart(): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}`, {
      headers: this.getHeaders(),
    });
  }

  updateMealQuantity(mealId: string, quantity: number): Observable<any> {
    const body = { mealId: mealId, quantity: quantity };
    return this.http.put<any>(`${this.baseUrl}/meals`, body, { headers: this.getHeaders()})
  }
}
