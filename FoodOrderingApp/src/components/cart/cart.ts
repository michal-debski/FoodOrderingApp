import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DialogService } from '../../services/dialog-service';
import { CartService } from '../../services/cart-service';
import { ShoppingCartResponse } from '../../models/shopping.cart.response';
import { Spinner } from '../shared/spinner/spinner';
import { firstValueFrom } from 'rxjs';

@Component({
  imports: [Spinner, CommonModule],
  selector: 'app-cart',
  templateUrl: './cart.html',
  styleUrl: './cart.css',
})
export class Cart {

  restaurantId: string | null = null;
  cart = signal<ShoppingCartResponse>({} as ShoppingCartResponse);
  isPreparing = signal(false);
  currentCart: any;

  constructor(
    private http: HttpClient,
    private dialogService: DialogService,
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log(localStorage.getItem('email'));
    
    this.restaurantId = localStorage.getItem('restaurantId');
    console.log('Restaurant ID loaded from localStorage:', this.restaurantId);

    this.cartService.getCart().subscribe({
      next: (data) => {
        console.log('Cart received:', data);
        this.cart.set(data);
        
        if (data && data.restaurantId) {
          this.restaurantId = data.restaurantId;
        }
      },
      error: (err) => console.error('Failed to fetch cart items: ', err)
    });
  }

  decreaseQuantity(item: any): void {
    const newQuantity = item.quantity - 1;
    this.updateQuantity(item.mealId, newQuantity);
  }
  
  increaseQuantity(item: any) {
    const newQuantity = item.quantity + 1;
    this.updateQuantity(item.mealId, newQuantity);
  }

  private updateQuantity(mealId: string, quantity: number): void {
    this.cartService.updateMealQuantity(mealId, quantity).subscribe({
      next: (updatedCartDTO: ShoppingCartResponse) => {
        console.log('Cart updated successfully:', updatedCartDTO);
        this.cart.set(updatedCartDTO); 
        if (updatedCartDTO && updatedCartDTO.restaurantId) {
          this.restaurantId = updatedCartDTO.restaurantId;
        }
      },
      error: (err) => console.error('Error updating quantity in UI', err)
    });
  }

  removeFromCart(mealId: string) { 
    this.dialogService.openConfirmDialog({
      type: 'delete',
      message: 'Do you confirm to remove meal from cart?'
    }).subscribe(confirmed => {
      if (confirmed) {
        this.cartService.removeMeal(mealId).subscribe({
          next: () => {
            console.log('Meal removed from cart, fetching fresh data...');
            this.cartService.getCart().subscribe({
              next: (freshCart) => {
                this.cart.set(freshCart);
                if (freshCart && freshCart.restaurantId) {
                  this.restaurantId = freshCart.restaurantId;
                }
              },
              error: (err) => console.error('Error refreshing cart', err)
            });
          },
          error: (err) => console.error('Error removing meal from cart', err)
        });
      }
    });
  }

  confirmCancelOrder() {
    this.dialogService
      .openConfirmDialog({
        type: 'finish',
        message: 'Do you confirm to cancel order?',
      })
      .subscribe((confirmed) => {
        if (confirmed) {
          this.cartService.clearCart().subscribe({
            next: () => {
              alert('Cart cleared successfully!');
              this.cart.set({} as ShoppingCartResponse);
              localStorage.removeItem('restaurantId'); 
              this.restaurantId = null;
            },
            error: (err) => console.error('Error clearing cart', err)
          });
        }
      });
  } 

  confirmFinishOrder() {
    this.dialogService
      .openConfirmDialog({
        type: 'finish',
        message: 'Do you confirm to finish order?',
      })
      .subscribe((confirmed) => {
        if (confirmed) {
          this.finishOrder();
        }
      });
  }

  async finishOrder() {
    const targetRestaurantId = this.restaurantId || this.cart().restaurantId;
    console.log('Wysyłanie zamówienia dla Restaurant ID:', targetRestaurantId);

    if (!targetRestaurantId || targetRestaurantId === 'null') {
      alert('Błąd: Nie można ustalić ID restauracji. Wróć do menu i dodaj produkt ponownie.');
      return;
    }

    const cartData = this.cart();
    if (!cartData.cartItemDTOList || !cartData.cartItemDTOList.length) {
      alert('Cart is empty!');
      return;
    }

    console.log('Sending order:', cartData.cartItemDTOList);
    const orderItems = cartData.cartItemDTOList.map(item => ({
      mealId: item.mealId,
      quantity: item.quantity,
      unitPrice: item.unitPrice
    }));

    const requestBody = { orderItems };
    this.isPreparing.set(true);

    try {
      await firstValueFrom(
        this.http.post<any>(
          // 👈 KROK D: Podstawiamy bezpieczną zmienną do adresu URL
          `http://localhost:8222/api/v1/orders/${targetRestaurantId}/order`,
          requestBody,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('token')}`,
              'X-User-Email': `${localStorage.getItem('email')}`,
            },
          }
        )
      );

      await new Promise((resolve) => setTimeout(resolve, 2000));
      this.isPreparing.set(false);

      await firstValueFrom(this.cartService.clearCart());
      localStorage.removeItem('active_restaurant_id'); // 👈 KROK E: Po udanym zamówieniu czyścimy pamięć

      alert('Successfully performed order!');
      this.router.navigate(['/restaurants/all-restaurants']);
    } catch (err: any) {
      this.isPreparing.set(false);
      if (err.status === 409 && err.error?.unavailableMeals) {
        const missing = err.error.unavailableMeals.join(', ');
        alert(`Cannot prepare meals: ${missing}, missing ingredients`);
      } else {
        alert('Error occurred while performing order.');
      }
    }
  }
}