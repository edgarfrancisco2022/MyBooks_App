import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../service/auth.guard';
import { AddItemsComponent } from './add-items/add-items.component';
import { ListsComponent } from './lists/lists.component';
import { MainComponent } from './main/main.component';
import { SearchComponent } from './search/search.component';
import { StatisticsComponent } from './statistics/statistics.component';

const routes: Routes = [
  {
    path: "",
    component: MainComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      {
        path: "",
        component: StatisticsComponent
      },
      {
        path: "statistics",
        component: StatisticsComponent
      },
      {
        path: "search",
        component: SearchComponent
      },
      {
        path: "add",
        component: AddItemsComponent
      },
      {
        path: "list",
        component: ListsComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class DashboardRoutingModule { }
