import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewChecked, AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { AppComponent } from 'src/app/app.component';
import { Book } from 'src/app/model/book';
import { BookService } from 'src/app/service/book.service';
import { DataBindingService } from 'src/app/service/data-binding.service';
import { SearchService } from 'src/app/service/search.service';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit, AfterViewInit {
  togglerClickedSubject$: BehaviorSubject<any>;
  togglerClickedSubscription$: Subscription = new Subscription();

  showPagination = true;
  numberOfItemsPerPage: number = 10;

  searchQuery = '';
  searchOption = 'title';
  searchResults: Book[] = [];

  displayBook: boolean = false;
  closeBook: boolean = false;

  searchScrollYPosition: number;

  hideSearch = false;

  bookInfo: Book;
  bookIndex: number;

  placeholderDiv: HTMLElement;

  constructor(private searchService: SearchService,
              private dataBindingService: DataBindingService,
              private bookService: BookService,
              private appComponent: AppComponent) { }

  ngOnInit(): void {
    this.dataBindingService.getSideNavSearchClicked().subscribe((searchClicked: boolean) => {
      this.onCloseBook();
    });

    this.togglerClickedSubject$ = this.dataBindingService.getTogglerClicked();

    this.getTogglerSubscription();

    this.getBooks(0, this.numberOfItemsPerPage);

    this.windowWidthEventListener();
  }

  ngAfterViewInit(): void {
    this.placeholderDiv = document.querySelector('.placeholder-div');

    this.dataBindingService.getBookHeight().subscribe((height: number) => {
      this.placeholderDiv.style.height = height.toString() + "px";
    });
  }

  getTogglerSubscription() {
    this.togglerClickedSubscription$ = this.togglerClickedSubject$.subscribe(
      (togglerClicked) => {
        let bookElement: HTMLElement = document.querySelector('.showBook');
        if (window.innerWidth <= 490) {
          if (togglerClicked) {
            if (bookElement != null) {
              bookElement.style.top = 'calc(64px + 110px)';
            }
          } else {
            if (bookElement != null) {
              bookElement.style.top = 'calc(64px + 72px)';
            }
          }
        } else {
          if (togglerClicked && window.innerWidth < 768) {
            if (bookElement != null) {
              bookElement.style.top = '117px';
            }
          } else {
            if (bookElement != null) {
              bookElement.style.top = '72px';
            }
          }
        }

      }
    );
  }

  getBooks(page: number, size: number) {
    this.bookService.getBooks(page, size).subscribe(
      (response: any)=> {
        this.showPagination = true;
        this.searchResults = response.content;
        this.dataBindingService.onGetBooksChange(response);
      },
      (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse);
      }
    );
  }

  setDisplayBook(book: Book, index: number) {

    this.dataBindingService.onBookClosed(false);

    document.getElementById('superZIndex').style.zIndex = '1';

    this.searchScrollYPosition = window.scrollY;

    this.bookInfo = book;
    this.bookIndex = index;

    let closeBookElement: HTMLElement = document.querySelector('.book');
    closeBookElement.style.display = "block";

    window.scroll({
      top: 0,
      left: 0,
      behavior: 'auto'
    });

    this.displayBook = true;

    setTimeout(() => {
      this.dataBindingService.onTogglerClicked(this.appComponent.isTogglerClicked());
    }, 3);

    setTimeout(() => {
      this.hideSearch = true;

      document.getElementById('superZIndex').style.zIndex = 'inherit';

      this.dataBindingService.onBookHeightChange(document.querySelector('app-book').getBoundingClientRect().height);

    }, 385);
  }

  getDisplayProperty() {
    return this.hideSearch === true ? 'none' : 'block';
  }

  getPlaceholderDisplayProperty() {
    return this.hideSearch === true ? 'block' : 'none';
  }

  onCloseBook() {
    this.dataBindingService.onBookClosed(true);

    this.closeBook = true;
    this.hideSearch = false;

    setTimeout(() => {
      let closeBookElement: HTMLElement = document.querySelector('.book');

      if (this.appComponent.isTogglerClicked()) {
        this.dataBindingService.onTogglerClicked(false);
      }

      if (closeBookElement != null) {
        closeBookElement.style.display = "none";
      }

      this.closeBook = false;
      this.displayBook = false;

      // document.scrollingElement.scrollTop += this.searchScrollYPosition;
    }, 385);
  }

  onUpdateBook(book: Book) {
    this.searchResults.splice(this.bookIndex, 1, book);
    this.bookInfo = book;
  }

  onDeleteBook() {
    this.searchResults.splice(this.bookIndex, 1);
  }


  onSearchQuerySubmit(form: NgForm) {

    this.searchService.searchByField(form.value['defaultSearchOption'], form.value['searchQuery']).subscribe(
      (response: Book[]) => {
        this.showPagination = false;
        this.searchResults = response;
      },
      (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse);
      }
    );

      this.searchQuery = null;
  }

  windowWidthEventListener() {
    onresize = (event) => {
      if (window.innerWidth >= 768) {
        if (this.appComponent.isTogglerClicked()) {
          this.dataBindingService.onTogglerClicked(false);
        }
      } else {
        if (this.appComponent.isTogglerClicked()) {
          this.dataBindingService.onTogglerClicked(true);
        } else {
          this.dataBindingService.onTogglerClicked(false);
        }
      }
      // this.dataBindingService.onTogglerClicked(this.appComponent.isTogglerClicked());
    }
  }

}
