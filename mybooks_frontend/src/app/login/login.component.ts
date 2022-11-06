import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../model/user';
import { AuthenticationService } from '../service/authentication.service';

/*
  registration and authentication source code based from the Udemy course
  JSON Web Token (JWT) with Spring Security And Angular
  https://www.udemy.com/course/jwt-springsecurity-angular/
*/

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  private JWT_TOKEN_HEADER: string = 'Jwt-Token';
  private subscriptions: Subscription[] = []; //to prevent memory leaks

  private demoAccountUsername: string = "demo";
  private demoAccountPassword: string = "1u0nMyRMsx";

  constructor(private authenticationService: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  public onLogin(user: User): void {

    //push to subscriptions to prevent memory leaks
    this.subscriptions.push(
      this.authenticationService.login(user).subscribe(
        (response: HttpResponse<User>) => {
          // 1 get token and add it to local storage
          const token = response.headers.get(this.JWT_TOKEN_HEADER);
          this.authenticationService.saveToken(token);

          // 2 add user to local storage
          this.authenticationService.addUserToLocalStorage(response.body);

          // 3 navigate to next component
          this.router.navigateByUrl('/main');
        },
        (errorResponse: HttpErrorResponse) => {
          console.log(errorResponse);
        }
      )
    );
  }

  isDisabled(loginForm: NgForm) {
    return !loginForm.valid;
  }

  demoAccountLogin() {
    if (this.authenticationService.isUserLoggedIn()) {
      this.authenticationService.logout();
    } else {
      let user: User = new User();
      user.username = this.demoAccountUsername;
      user.password = this.demoAccountPassword;

      this.subscriptions.push(
        this.authenticationService.login(user).subscribe(
          (response: HttpResponse<User>) => {
            // 1 get token and add it to local storage
            const token = response.headers.get(this.JWT_TOKEN_HEADER);
            this.authenticationService.saveToken(token);

            // 2 add user to local storage
            this.authenticationService.addUserToLocalStorage(response.body);

            // 3 navigate to next component
            this.router.navigateByUrl('/main');
          },
          (errorResponse: HttpErrorResponse) => {
            console.log(errorResponse);
          }
        )
      );
    }

  }
}
