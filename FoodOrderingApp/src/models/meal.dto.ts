import {IngredientForMealDTO} from './meal.ingredient.dto';

export interface MealDTO {
  mealId: string;
  name: string;
  category: string;
  description: string;
  price: number;
  ingredientsForMeal: IngredientForMealDTO[];
  restaurantId: string;
}

