import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { DataBindingService } from 'src/app/service/data-binding.service';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css']
})
export class SideNavComponent implements OnInit {

  activeFeature: string = 'mybooks';
  searchClicked = false;

  constructor(private dataBindingService: DataBindingService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
 
  }

  classActive(activeClass: string) {
    this.activeFeature = activeClass;
    this.searchClicked = true;
    this.dataBindingService.onSideNavSearchClicked(this.searchClicked);
  }

}
