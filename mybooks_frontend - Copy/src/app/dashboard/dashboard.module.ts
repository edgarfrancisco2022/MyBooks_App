import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { MainComponent } from './main/main.component';
import { SideNavComponent } from './side-nav/side-nav.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { SearchComponent } from './search/search.component';
import { FormsModule } from '@angular/forms';
import { SearchService } from '../service/search.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from '../service/auth.interceptor';
import { AddItemsComponent } from './add-items/add-items.component';
import { BookComponent } from './search/book/book.component';
import { UpdateBookComponent } from './search/book/update-book/update-book.component';
import { ListsComponent } from './lists/lists.component';
import { PaginationComponent } from './search/pagination/pagination.component';


@NgModule({
  declarations: [
    MainComponent,
    SideNavComponent,
    StatisticsComponent,
    SearchComponent,
    AddItemsComponent,
    BookComponent,
    UpdateBookComponent,
    ListsComponent,
    PaginationComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule
  ]
})
export class DashboardModule { }
