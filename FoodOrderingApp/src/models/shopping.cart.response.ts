import { ShoppingCartItemResponse } from "./shopping.cart.item.response";

export interface ShoppingCartResponse {
      customerEmail: string;
      restaurantId: string;
      cartItemDTOList: ShoppingCartItemResponse[];
      totalPrice: number;
}