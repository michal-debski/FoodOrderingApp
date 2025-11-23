import { OrderItemWithMealResponse } from "./order.item.with.meal.response";

export interface OrderWithMealDataResponse {
     orderNumber: string;
      totalPrice: number;
      status: string;
      customerEmail: string;
      orderDate: string;
      orderItems: OrderItemWithMealResponse[];
      isCancellable: boolean;
}