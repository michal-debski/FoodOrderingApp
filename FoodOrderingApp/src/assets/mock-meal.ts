import {MealDTO} from '../models/meal.dto';

export const MOCK_MEALS: MealDTO[] = [
  {
    mealId: "1",
    name: 'Margherita Pizza',
    category: 'Pizza',
    description: 'Classic pizza with tomatoes, mozzarella, and basil',
    price: 9.99,
    ingredientsForMeal: [
      { name: 'Tomato Sauce', quantity: 150, unit: 'ml' },
      { name: 'Mozzarella', quantity: 100, unit: 'g' },
      { name: 'Basil', quantity: 5, unit: 'g' },
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
      { name: 'Beef Patty', quantity: 150, unit: 'g' },
      { name: 'Cheddar Cheese', quantity: 50, unit: 'g' },
      { name: 'Lettuce', quantity: 30, unit: 'g' },
      { name: 'Tomato', quantity: 50, unit: 'g' },
      { name: 'Onion', quantity: 30, unit: 'g' },
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
      { name: 'Romaine Lettuce', quantity: 100, unit: 'g' },
      { name: 'Parmesan', quantity: 20, unit: 'g' },
      { name: 'Croutons', quantity: 30, unit: 'g' },
      { name: 'Caesar Dressing', quantity: 50, unit: 'ml' },
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
      { name: 'Spaghetti', quantity: 100, unit: 'g' },
      { name: 'Ground Beef', quantity: 150, unit: 'g' },
      { name: 'Tomato Sauce', quantity: 150, unit: 'ml' },
      { name: 'Garlic', quantity: 5, unit: 'g' },
      { name: 'Onion', quantity: 50, unit: 'g' },
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
      { name: 'Grilled Chicken', quantity: 120, unit: 'g' },
      { name: 'Lettuce', quantity: 30, unit: 'g' },
      { name: 'Tomato', quantity: 40, unit: 'g' },
      { name: 'Mayonnaise', quantity: 20, unit: 'g' },
      { name: 'Wrap Bread', quantity: 1, unit: 'pcs' },
    ],
    restaurantId: '4',
  }
];
