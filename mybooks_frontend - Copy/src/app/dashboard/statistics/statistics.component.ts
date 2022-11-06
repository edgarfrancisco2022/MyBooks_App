import { Component, OnInit } from '@angular/core';
import { Author } from 'src/app/model/author';
import { Book } from 'src/app/model/book';
import { StatisticsResponse } from 'src/app/model/statistics-response';
import { StatisticsService } from 'src/app/service/statistics.service';


@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  statisticsResponse: StatisticsResponse = new StatisticsResponse();

  constructor(private statisticsService: StatisticsService) { }

  ngOnInit(): void {
    this.getStatistics();
  }

  getStatistics() {
    this.statisticsService.getStatistics().subscribe(
      (response) => {
        console.log(response);
        // this.statisticsResponse.totalBooks = response.totalBooks;
        // this.statisticsResponse.totalAuthors = response.totalAuthors;
        // this.statisticsResponse.totalLists = response.totalLists;
        // this.statisticsResponse.totalPublishers = response.totalPublishers;
        // this.statisticsResponse.totalCategories = response.totalCategories;
        // this.statisticsResponse.totalTags = response.totalTags;
        this.statisticsResponse = response;
      },
      (error) => {
        console.log(error);
      }
    )
  }

}
