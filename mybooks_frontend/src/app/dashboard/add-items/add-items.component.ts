import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/model/book';
import { Author } from 'src/app/model/author';
import { Tag } from 'src/app/model/tag';
import { Publisher } from 'src/app/model/publisher';
import { Category } from 'src/app/model/category';
import { Collection } from 'src/app/model/collection';
import { BookService } from 'src/app/service/book.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-items',
  templateUrl: './add-items.component.html',
  styleUrls: ['./add-items.component.css']
})
export class AddItemsComponent implements OnInit {

  title: string = '';
  subtitle: string;
  year: string;
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

  atLeastOneAuthorAdded: boolean = this.authors.length > 0 ? true : false;
  submitDisabled = true;

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

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
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

  onAddItemFormSubmit() {
    let book: Book = this.createBook();

    this.bookService.addNewBook(book).subscribe(
      (response: Book) => {

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
