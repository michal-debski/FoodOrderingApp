import {MealDTO} from '../models/meal.dto';

export const MOCK_MEALS: MealDTO[] = [
  {
    mealId: "1",
    name: 'Margherita Pizza',
    category: 'Pizza',
    description: 'Classic pizza with tomatoes, mozzarella, and basil',
    price: 9.99,
    ingredientsForMeal: [
      { name: 'Tomato Sauce', quantity: 150, unitName: 'ml' },
      { name: 'Mozzarella', quantity: 100, unitName: 'g' },
      { name: 'Basil', quantity: 5, unitName: 'g' },
    ],
    restaurantId: '1',
  },
  {
    mealId: "2",
    name: 'Cheeseburger',
    category: 'Burger',
    description: 'Beef patty with cheddar, lettuce, tomato, and onion',
    price: 11.50,
    ingredientsForMeal: [
      { name: 'Beef Patty', quantity: 150, unitName: 'g' },
      { name: 'Cheddar Cheese', quantity: 50, unitName: 'g' },
      { name: 'Lettuce', quantity: 30, unitName: 'g' },
      { name: 'Tomato', quantity: 50, unitName: 'g' },
      { name: 'Onion', quantity: 30, unitName: 'g' },
    ],
    restaurantId: '1',
  },
  {
    mealId: "3",
    name: 'Caesar Salad',
    category: 'Salad',
    description: 'Romaine lettuce with Caesar dressing and croutons',
    price: 7.99,
    ingredientsForMeal: [
      { name: 'Romaine Lettuce', quantity: 100, unitName: 'g' },
      { name: 'Parmesan', quantity: 20, unitName: 'g' },
      { name: 'Croutons', quantity: 30, unitName: 'g' },
      { name: 'Caesar Dressing', quantity: 50, unitName: 'ml' },
    ],
    restaurantId: '2',
  },
  {
    mealId: "4",
    name: 'Spaghetti Bolognese',
    category: 'Pasta',
    description: 'Spaghetti with a rich meat sauce',
    price: 12.49,
    ingredientsForMeal: [
      { name: 'Spaghetti', quantity: 100, unitName: 'g' },
      { name: 'Ground Beef', quantity: 150, unitName: 'g' },
      { name: 'Tomato Sauce', quantity: 150, unitName: 'ml' },
      { name: 'Garlic', quantity: 5, unitName: 'g' },
      { name: 'Onion', quantity: 50, unitName: 'g' },
    ],
    restaurantId: '3',
  },
  {
    mealId: "5",
    name: 'Grilled Chicken Wrap',
    category: 'Wrap',
    description: 'Grilled chicken with lettuce, tomato, and mayo in a wrap',
    price: 8.75,
    ingredientsForMeal: [
      { name: 'Grilled Chicken', quantity: 120, unitName: 'g' },
      { name: 'Lettuce', quantity: 30, unitName: 'g' },
      { name: 'Tomato', quantity: 40, unitName: 'g' },
      { name: 'Mayonnaise', quantity: 20, unitName: 'g' },
      { name: 'Wrap Bread', quantity: 1, unitName: 'pcs' },
    ],
    restaurantId: '4',
  }
];
