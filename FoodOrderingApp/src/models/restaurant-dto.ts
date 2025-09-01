import {RestaurantStreetDTO} from './restaurant-street-dto';

export interface RestaurantDTO {
  restaurantName: string;
  phone: string;
  email: string;
  address: string;
  ownerEmail: string;
  restaurantStreets: RestaurantStreetDTO[];
}
