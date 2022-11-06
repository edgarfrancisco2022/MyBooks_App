import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Author } from 'src/app/model/author';
import { Book } from 'src/app/model/book';
import { Category } from 'src/app/model/category';
import { Collection } from 'src/app/model/collection';
import { Publisher } from 'src/app/model/publisher';
import { Tag } from 'src/app/model/tag';
import { BookService } from 'src/app/service/book.service';
import { SearchComponent } from '../../search.component';
import { BookComponent } from '../book.component';

@Component({
  selector: 'app-update-book',
  templateUrl: './update-book.component.html',
  styleUrls: ['./update-book.component.css']
})
export class UpdateBookComponent implements OnInit {

  title: string = '';
  subtitle: string;
  year: string = '';
  pages: number;
  copies: number;
  callnumber: string = '';

  publisherName: string = '';
  categoryName: string = '';
  collectionName: string;

  firstName: string;
  middleName: string;
  lastName: string;

  tagName: string;

  authors: Author[] = [];
  tags: Tag[] = [];

  // Validation
  inputValidation: Object = {
    "title": true,
    "publisher": true,
    "year": true,
    "callnumber": true,
    "category": true
  }

  atLeastOneAuthorAdded: boolean;
  submitDisabled = true;

  constructor(private bookService: BookService,
              private bookComponent: BookComponent,
              private searchComponent: SearchComponent) { }

  ngOnInit(): void {
    let bookInfo: Book = this.bookComponent.getBook();

    this.title = bookInfo.title;
    this.subtitle = bookInfo.subtitle;
    this.year = bookInfo.year;
    this.pages = bookInfo.numberOfPages;
    this.copies = bookInfo.numberOfCopies;
    this.callnumber = bookInfo.callNumber;

    if (bookInfo.publisher != null) {
      this.publisherName = bookInfo.publisher.publisherName;
    }

    if (bookInfo.category != null) {
      this.categoryName = bookInfo.category.categoryName;
    }

    if (bookInfo.collection != null) {
      this.collectionName = bookInfo.collection.collectionName;
    }

    if (bookInfo.authors != null) {
      this.authors = bookInfo.authors;
      for (let auth of this.authors) {
        auth.id = null;
      }
    }

    this.atLeastOneAuthorAdded = this.authors.length > 0 ? true : false;

    if (bookInfo.tags != null) {
      this.tags = bookInfo.tags;
      for (let tag of this.tags) {
        tag.id = null;
      }
    }
  }

  isDisabled() {

    if (
      this.title.length >= 2 && this.publisherName.length >= 2 && (!isNaN(Number(this.year)) && this.year.length > 0) && this.callnumber.length >= 2 && this.categoryName.length >= 2 && this.atLeastOneAuthorAdded
      ) {

      this.submitDisabled = false;
    } else {
      this.submitDisabled = true;
    }

    return this.submitDisabled;
  }

  onAddAuthor() {
    if (this.firstName && this.lastName) {
      let author = new Author();
      author.firstName = this.firstName;
      author.middleName = this.middleName;
      author.lastName = this.lastName;
      if (this.middleName != null) {
        author.fullName = `${this.firstName} ${this.middleName} ${this.lastName}`;
      } else {
        author.fullName = `${this.firstName} ${this.lastName}`;
      }

      this.authors.push(author);
      this.atLeastOneAuthorAdded = true;
      this.firstName = null;
      this.middleName = null;
      this.lastName = null;
    }
  }

  onRemoveAuthor(i: number) {
    this.authors.splice(i, 1);
    if (this.authors.length === 0) {
      this.atLeastOneAuthorAdded = false;
    }
  }

  onAddTag() {
    if (this.tagName) {
      let tag = new Tag();
      tag.tagName = this.tagName;
      this.tags.push(tag);
      this.tagName = null;
    }
  }

  onRemoveTag(i: number) {
    this.tags.splice(i, 1);
  }

  onUpdateItemFormSubmit() {
    // console.log('before book create');
    let book: Book = this.createBook();
    console.log('after book create');
    console.log(book);

    this.bookService.updateBook(book).subscribe(
      (response: Book) => {
        console.log('backend: ' + JSON.stringify(response));
        this.searchComponent.onUpdateBook(response);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );

    this.clearForm();
  }

  createBook(): Book {
    if (this.atLeastOneAuthorAdded) {
      let book = new Book();
      book.title = this.title;
      book.subtitle = this.subtitle;
      book.year = this.year;
      book.numberOfPages = this.pages;
      book.numberOfCopies = this.copies;
      book.callNumber = this.callnumber;

      book.authors = this.authors;
      book.tags = this.tags;

      let publisher = new Publisher();
      publisher.publisherName = this.publisherName;
      book.publisher = publisher;

      let category = new Category();
      category.categoryName = this.categoryName;
      book.category = category;

      if (this.collectionName) {
        let collection = new Collection();
        collection.collectionName = this.collectionName;
        book.collection = collection;
      }

      console.log(book);
      return book;
    }

    return null;
  }

  clearForm() {
    this.title = '';
    this.subtitle = null;
    this.publisherName = '';
    this.year = '';
    this.pages = null;
    this.copies = null;
    this.callnumber = '';
    this.categoryName = '';
    this.collectionName = null;

    this.firstName = null;
    this.middleName = null;
    this.lastName = null;

    this.tagName = null;

    this.authors = [];
    this.tags = [];

    this.submitDisabled = true;
  }

  // Input fields validation---------------------

  validateTextField(input: string, variableName: string) {
    console.log(input);
    console.log(variableName);
    if (input === undefined || input.length < 2) {
      this.inputValidation[variableName] = false;
    } else {
      this.inputValidation[variableName] = true;
    }
  }

  validateNumberField(input: string, variableName: string) {
    if (input === undefined || input.length === 0 || isNaN(Number(input)) ) {
      this.inputValidation[variableName] = false;
    } else {
      this.inputValidation[variableName] = true;
    }
  }

}
