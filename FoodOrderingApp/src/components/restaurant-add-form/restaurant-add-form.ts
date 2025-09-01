import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {RestaurantService} from '../../services/restaurant-service';
import {Router} from '@angular/router';
import {RestaurantRequest} from '../../models/restaurant.request';
import {tap} from 'rxjs';

@Component({
  selector: 'app-restaurant-add-form',
    imports: [
        ReactiveFormsModule
    ],
  templateUrl: './restaurant-add-form.html',
  styleUrl: './restaurant-add-form.css'
})
export class RestaurantAddForm {
  constructor(
    private restaurant: RestaurantService,
    private router: Router
  ) {}

  addRestaurantForm : FormGroup = new FormGroup({
    email: new FormControl(''),
    password: new FormControl('')
  });

  request: RestaurantRequest = new RestaurantRequest();

  addRestaurant() {
    const formValue = this.addRestaurantForm.value;
    console.log("Add restaurant function called");

    this.request.restaurantName = formValue.restaurantName;
    this.request.email = formValue.email;
    this.request.address = formValue.address;
    this.request.phone = formValue.phone;

    this.restaurant.addRestaurant(this.request).pipe(
      tap({
        next: (res: any) => {
            this.router.navigate(['/restaurants']);
        },
        error: (err) => {
          console.error("Error Received Response: ", err);
        }
      })
    ).subscribe();
  }
}
