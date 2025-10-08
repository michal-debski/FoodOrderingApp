import {RestaurantStreetDTO} from './restaurant-street-dto';

export interface RestaurantDTO {
  restaurantId: string;
  restaurantName: string;
  phone: string;
  email: string;
  address: string;
  ownerEmail: string;
  restaurantStreets: RestaurantStreetDTO[];
}
