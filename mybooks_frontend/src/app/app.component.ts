import { Component } from '@angular/core';
import { AuthenticationService } from './service/authentication.service';
import { DataBindingService } from './service/data-binding.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title: any;

  private togglerClicked: boolean = false;

  constructor(private authenticationService: AuthenticationService,
              private dataBindingService: DataBindingService) {}

  isTogglerClicked(): boolean {
    return this.togglerClicked;
  }

  setTogglerClicked(togglerClicked: boolean): void {
    this.togglerClicked = togglerClicked;
  }

  onTogglerClicked() {
    this.togglerClicked === false ? this.togglerClicked = true : this.togglerClicked = false;
    this.dataBindingService.onTogglerClicked(this.togglerClicked);
  }

  isUserLoggedIn() {
    return this.authenticationService.isUserLoggedIn();
  }

  onLogout() {
    this.authenticationService.logout();
  }
}
