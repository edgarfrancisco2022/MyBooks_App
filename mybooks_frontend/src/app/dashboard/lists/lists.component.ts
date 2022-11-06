import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ListService } from 'src/app/service/list.service';
import { map } from 'rxjs/operators';
import { CustomCollection } from 'src/app/model/custom-collection';
import { CustomCollectionResponse } from 'src/app/model/custom-collection-response';

@Component({
  selector: 'app-lists',
  templateUrl: './lists.component.html',
  styleUrls: ['./lists.component.css']
})
export class ListsComponent implements OnInit {

  newList: string;
  listOfLists: CustomCollectionResponse[];
  listIndex: number;

  constructor(private listService: ListService) { }

  ngOnInit(): void {

    this.getLists()
  }

  getLists() {
    this.listService.getLists().subscribe(
      (response: CustomCollectionResponse[]) => {
        this.listOfLists = response;
      },
      (error: HttpErrorResponse) => {
        console.log(JSON.stringify(error));
      }
    );
  }

  onAddNewList(addNewList: NgForm) {
    this.listService.addNewList(this.newList).subscribe(
      (response: any) => {
        this.newList = null;
        this.getLists();
      },
      (error: HttpErrorResponse) => {
        console.log(error); // fix bug
      }
    );
  }

  onDeleteList(event: Event, index: number) {
    this.listIndex = index;
    document.getElementById('delete-list-modal').click();
  }

  onDeleteListConfirmed() {
    this.listService.deleteList(this.listOfLists[this.listIndex].customCollection.customCollectionName).subscribe(
      (response: any) => {
        this.getLists();
      },
      (error: HttpErrorResponse) => {
        console.log(error); // fix bug
      }
    );
  }

  isDisabled(): boolean {
    if (this.newList) {
      return false;
    }
    return true;
  }

  onRemoveBookFromList(listIndex: number, bookIndex: number): void {
    this.listService.deleteBookFromList(this.listOfLists[listIndex].customCollection
      .customCollectionName,
      this.listOfLists[listIndex].books[bookIndex].callNumber).subscribe(
        (response) => {
          this.listOfLists[listIndex].books.splice(bookIndex, 1);
        },
        (error) => {
          console.log(error);
        }
      );
  }
}
