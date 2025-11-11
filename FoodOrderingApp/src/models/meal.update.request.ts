import {IngredientForMealDTO} from './meal.ingredient.dto';

export interface MealUpdateRequest {
  mealId: string,
  name: string;
  description: string;
  price: number;
  ingredientsForMeal: IngredientForMealDTO[];
}

