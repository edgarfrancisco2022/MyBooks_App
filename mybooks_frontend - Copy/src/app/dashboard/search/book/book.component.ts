import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, HostListener, Input, OnInit, ViewChild } from '@angular/core';
import { debounceTime, fromEvent, map, Observable, pipe, tap } from 'rxjs';
import { Book } from 'src/app/model/book';
import { CustomCollection } from 'src/app/model/custom-collection';
import { CustomCollectionResponse } from 'src/app/model/custom-collection-response';
import { CustomHttpResponse } from 'src/app/model/custom-http-response';
import { BookService } from 'src/app/service/book.service';
import { DataBindingService } from 'src/app/service/data-binding.service';
import { ListService } from 'src/app/service/list.service';
import { SearchComponent } from '../search.component';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit, AfterViewInit {

  @Input() bookInfo: Book;

  updateBook: boolean = false;

  listSearchQuery: string;

  listOfLists: CustomCollection[];

  selectedList: string = 'Select list';

  @ViewChild('listSearchInput', {static: true}) input: ElementRef;

  constructor(private dataBindingService: DataBindingService,
              private bookService: BookService,
              private searchComponent: SearchComponent,
              private listService: ListService) {


  }

  ngOnInit(): void {
    this.dataBindingService.getBookClosed().subscribe((bookClosed: boolean) => {
      if (bookClosed = true) {
        setTimeout(() => {
          this.updateBook = false;
        }, 385)
      }
    });
  }

  ngAfterViewInit(): void {
    const searchList$ = fromEvent<any>(this.input.nativeElement, 'keyup').pipe(
      debounceTime(500)
    ).subscribe(
      (event) => {
        this.listService.searchBySearchQuery(event.target.value).subscribe(
          (response: CustomCollection[]) => {
            console.log(JSON.stringify(response));
            this.listOfLists = response;
          },
          (error: HttpErrorResponse) => {
            console.log(JSON.stringify(error));
          }
        );
      }
    );

    document.addEventListener('click', (e: Event) => {

      let listExpaned: HTMLElement = document.querySelector('.list-expanded');

      if (listExpaned != null && listExpaned.style.display === 'block') {

        if (e.target != document.querySelector('.list-expanded') &&
            e.target != document.querySelector('.list-expanded-input') &&
            e.target != document.querySelector('.list-expanded-input input') &&
            e.target != document.querySelector('.list-expanded-input span') &&
            e.target != document.querySelector('.lists-dropdown') &&
            e.target != document.querySelector('.lists-dropdown .select-list') &&
            e.target != document.querySelector('.lists-dropdown span')) {

          console.log(e.target);

          listExpaned.style.display = 'none';
          let listInput: any = document.getElementById('listInput');
          listInput.value = null;
        }
      }
    });
  }

  getBook(): Book {
    return this.bookInfo;
  }

  onBookDetail(): void {
    this.updateBook = false;

    setTimeout(() => {
      this.dataBindingService.onBookHeightChange(document.querySelector('.book-detail')
      .getBoundingClientRect().height);
    }, 10);
  }

  onUpdateBook(): void {
    this.updateBook = true;

    setTimeout(() => {
      this.dataBindingService.onBookHeightChange(document.querySelector('.book-detail')
      .getBoundingClientRect().height);
    }, 10);

  }

  onDeleteBook(): void {
    document.getElementById('deleteButton').click();
  }

  onDeleteBookConfirmed(): void {
    document.getElementById('closeModal').click();

    this.bookService.deleteBook(this.bookInfo.callNumber).subscribe(
      (response: CustomHttpResponse) => {
        console.log(response);
      },
      (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse);
      }
    );

    this.onCloseBook();
    this.searchComponent.onDeleteBook();

  }

  onCloseBook(): void {
    this.dataBindingService.onSideNavSearchClicked(true);
  }

  onAddToList(): void {
    this.selectedList = 'Select list';
    document.getElementById('addToListButton').click();
  }

  onListDropdownClicked(): void {
    let listExpanded: HTMLElement = document.querySelector('.list-expanded');
    let listsDropdown: HTMLElement = document.querySelector('.lists-dropdown');
    if (listExpanded.style.display === 'none') {

      this.listService.getLists().subscribe(
        (response: CustomCollectionResponse[]) => {
          let customCollections: CustomCollection[] = [];

          for (let i = 0; i < response.length; i++) {
            customCollections.push(response[i].customCollection);
          }

          this.listOfLists = customCollections;
        },
        (error: HttpErrorResponse) => {
          console.log(JSON.stringify(error));
        }
      );

      setTimeout(() => {
        listExpanded.style.display = 'block';
        listsDropdown.style.borderRadius = '5px 5px 0 0';
      }, 50);

    } else {
      listExpanded.style.display = 'none';
      listsDropdown.style.borderRadius = '5px';
    }
  }

  onListSelected(list: string): void {
    this.selectedList = list;
  }

  onAddToListConfirmed() {
    this.listService.addBookToList(this.selectedList, this.bookInfo.callNumber).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );

    let listModal: any = document.getElementById('addToListModal');
  }

}


