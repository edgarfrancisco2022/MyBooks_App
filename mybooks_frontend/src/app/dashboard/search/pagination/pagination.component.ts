import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewChecked, AfterViewInit, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { NonNullableFormBuilder } from '@angular/forms';
import { BehaviorSubject, lastValueFrom, skip, Subscription } from 'rxjs';
import { BookService } from 'src/app/service/book.service';
import { DataBindingService } from 'src/app/service/data-binding.service';
import { SearchService } from 'src/app/service/search.service';
import { SearchComponent } from '../search.component';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit, OnDestroy {

  data$: BehaviorSubject<any>;
  subscription$: Subscription = new Subscription();

  @Input() numberOfItemsPerPage: number;

  pageableObject: any = null;
  totalPages: number[] = [];
  totalPagesLength: number;

  currentPage: number = 1;
  maxNumberOfPagesDisplayed: number = 3;

  position: number = 1;



  constructor(private bookService: BookService,
              private searchComponent: SearchComponent,
              private dataBindingService: DataBindingService) {}

  ngOnDestroy(): void {
    this.subscription$.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();

  }

  loadData() {
    setTimeout(() => {
      this.data$ = this.dataBindingService.getBooks();
      this.subscription$ = this.data$.subscribe(
        (response) => {
          this.pageableObject = response;
          this.initPagination(this.currentPage);
        },
        (error) => {
          console.log(error);
        }
      );
    }, 20)
  }

  getBooks(page: number, size: number) {
    //window.scrollTo(0, 0);
    window.scrollTo({ top: 0, behavior: 'auto' });

    let pageItemElement = document.getElementById('page' + this.currentPage);
    pageItemElement.classList.remove('active');
    this.currentPage = page + 1;
    this.searchComponent.getBooks(page, size);
  }

  initPagination(page: number) {
    this.totalPages = [];

    for (let i = 1; i <= this.pageableObject.totalPages; i++) {
      this.totalPages.push(i);
    }
    this.totalPagesLength = this.totalPages.length;

    if (this.totalPagesLength > 0) {
      this.currentPage = page;
      setTimeout(() => {
        let pageItemElement = document.getElementById('page' + page);
        pageItemElement.classList.add('active');
      }, 20);

    } else {
        this.currentPage = 0;
    }

    this.checkPrevAndNextDisabled();

  }

  checkPrevAndNextDisabled() {
    let paginationPrevElement = document.querySelector('.pagination-prev');
    let paginationNextElement = document.querySelector('.pagination-next');

    if (this.position >= this.totalPagesLength - 2) {
      paginationNextElement.classList.add('disabled');
    } else {
      paginationNextElement.classList.remove('disabled');
    }

    if (this.position == 1) {
      paginationPrevElement.classList.add('disabled');
    } else {
      paginationPrevElement.classList.remove('disabled');
    }
  }

  isPageItemActive(i: number) {
    if (this.currentPage === i) {
      return true;
    }
    return false;
  }

  // ----------------- pagination logic ---------------------- //
  onPageLinkClicked(iIndex: number) {
    this.getBooks(iIndex, this.numberOfItemsPerPage);
    this.currentPage = iIndex + 1;
  }

  onPrevPageClicked() {
    this.position -= 1;
    this.getBooks(this.currentPage - 2, this.numberOfItemsPerPage);
  }

  onNextPageClicked() {
    this.position += 1;
    this.getBooks(this.currentPage, this.numberOfItemsPerPage);
  }
}
