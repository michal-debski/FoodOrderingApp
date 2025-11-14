import {IngredientForMealDTO} from './meal.ingredient.dto';

export interface MealUpdateRequest {
  name: string;
  description: string;
  price: number;
  ingredientsForMeal: IngredientForMealDTO[];
}

