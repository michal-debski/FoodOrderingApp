import {RestaurantDTO} from '../models/restaurant-dto';

export const MOCK_RESTAURANTS: RestaurantDTO[] = [
  {
    restaurantName: "Bella Italia",
    phone: "+48 123 456 789",
    email: "contact@bellaitalia.pl",
    address: "ul. Włoska 5, 00-001 Warszawa",
    ownerEmail: "owner@bellaitalia.pl",
    restaurantStreets: [
      {
        restaurantId: "1",
        street: { streetId: 1, name: "ul. Włoska" }
      }
    ]
  },
  {
    restaurantName: "Sushi Zen",
    phone: "+48 234 567 890",
    email: "info@sushizen.pl",
    address: "ul. Japońska 12, 00-002 Kraków",
    ownerEmail: "takashi@sushizen.pl",
    restaurantStreets: [
      {
        restaurantId: "2",
        street: { streetId: 2, name: "ul. Słowacka" }
      }
    ]
  },
  {
    restaurantName: "La Fiesta",
    phone: "+48 345 678 901",
    email: "kontakt@lafiesta.pl",
    address: "ul. Hiszpańska 3, 00-003 Wrocław",
    ownerEmail: "maria@lafiesta.pl",
    restaurantStreets: [
      {
        restaurantId: "3",
        street: { streetId: 3, name: "ul. Czeska" }
      }
    ]
  },
  {
    restaurantName: "Burger House",
    phone: "+48 456 789 012",
    email: "hello@burgerhouse.pl",
    address: "ul. Amerykańska 20, 00-004 Gdańsk",
    ownerEmail: "john@burgerhouse.pl",
    restaurantStreets: [
      {
        restaurantId: "4",
        street: { streetId: 4, name: "ul. Szwedzka" }
      }
    ]
  },
  {
    restaurantName: "Curry Corner",
    phone: "+48 567 890 123",
    email: "support@currycorner.pl",
    address: "ul. Indyjska 8, 00-005 Poznań",
    ownerEmail: "rahul@currycorner.pl",
    restaurantStreets: [
      {
        restaurantId: "5",
        street: { streetId: 5, name: "ul. Indyjska" }
      }
    ]
  }
];


