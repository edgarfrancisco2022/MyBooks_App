import { Book } from "./book";

export class StatisticsResponse {

  totalBooks: number;
  totalAuthors: number;
  totalLists: number;
  totalPublishers: number;
  totalCategories: number;
  totalTags: number;
  latestBooksAdded: Book[];

  constructor() {

  }
}
