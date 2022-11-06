import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataBindingService {
  private togglerClicked$ = new BehaviorSubject(false);
  private sideNavSearchClicked$ = new BehaviorSubject(true);
  private bookClosed$ = new BehaviorSubject(true);
  private bookHeight$ = new BehaviorSubject<number>(0);
  private getBooks$ = new BehaviorSubject<any>(null);

  constructor() { }

  onTogglerClicked(togglerClicked: boolean): void {
    this.togglerClicked$.next(togglerClicked);
  }

  getTogglerClicked(): BehaviorSubject<boolean> {
    return this.togglerClicked$;
  }

  onSideNavSearchClicked(searchClicked: boolean): void {
    this.sideNavSearchClicked$.next(searchClicked);
  }

  getSideNavSearchClicked(): BehaviorSubject<boolean> {
    return this.sideNavSearchClicked$;
  }

  onBookClosed(bookClosed: boolean): void {
    this.bookClosed$.next(bookClosed);
  }

  getBookClosed(): BehaviorSubject<boolean> {
    return this.bookClosed$;
  }

  onBookHeightChange(bookHeight: number): void {
    this.bookHeight$.next(bookHeight);
  }

  getBookHeight(): BehaviorSubject<number> {
    return this.bookHeight$;
  }

  onGetBooksChange(searchResults: any): void {
    console.log('changing books');
    this.getBooks$.next(searchResults);
  }

  getBooks(): BehaviorSubject<any> {
    return this.getBooks$;
  }

}
