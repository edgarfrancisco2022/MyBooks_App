import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GetStartedComponent } from './get-started/get-started.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './service/auth.guard';
import { HomeComponent } from './home/home.component';
import { AuthenticationService } from './service/authentication.service';
import { SearchService } from './service/search.service';
import { AuthInterceptor } from './service/auth.interceptor';
import { BookService } from './service/book.service';

@NgModule({
  declarations: [
    AppComponent,
    GetStartedComponent,
    LoginComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    AuthGuard, AuthenticationService, SearchService, BookService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
