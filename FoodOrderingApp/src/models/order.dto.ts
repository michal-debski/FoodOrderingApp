import { OrderItemDTO } from './order.item.dto';

export interface OrderDTO {
  orderNumber: string;
  totalPrice: number;
  status: string;
  customerEmail: string;
  orderDate: string;
  orderItems: OrderItemDTO[];
  isCancellable: boolean;
}
