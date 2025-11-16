import { Category } from './category';
import {IngredientForMealDTO} from './meal.ingredient.dto';

export interface MealDTO {
  mealId: string;
  name: string;
  category: Category;
  description: string;
  price: number;
  ingredientsForMeal: IngredientForMealDTO[];
  restaurantId: string;
}

