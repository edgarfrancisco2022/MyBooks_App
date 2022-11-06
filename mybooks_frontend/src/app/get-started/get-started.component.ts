import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { AuthenticationService } from '../service/authentication.service';

/*
  registration and authentication source code based from the Udemy course
  JSON Web Token (JWT) with Spring Security And Angular
  https://www.udemy.com/course/jwt-springsecurity-angular/
*/

@Component({
  selector: 'app-get-started',
  templateUrl: './get-started.component.html',
  styleUrls: ['./get-started.component.css']
})
export class GetStartedComponent implements OnInit {
  public accountCreated: boolean = false;

  constructor(private authenticationService: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onRegister(user: User, registerForm: NgForm): void {
    this.authenticationService.register(user).subscribe(
      (response: User) => {
        console.log(response);
        registerForm.resetForm();
        this.accountCreated = true;
      },
      (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse);
      }
    );
  }

  isDisabled(registerForm: NgForm): boolean {
    return !registerForm.valid;
  }
}
