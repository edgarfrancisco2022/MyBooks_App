import { Author } from "./author";
import { Category } from "./category";
import { Collection } from "./collection";
import { CustomCollection } from "./custom-collection";
import { Publisher } from "./publisher";
import { Tag } from "./tag";

export class Book {
  dateAdded: Date;
  callNumber: string;
  title: string;
  subtitle: string;
  year: string;
  numberOfPages: number;
  numberOfCopies: number;
  description: string;
  authors: Author[];
  tags: Tag[];
  customCollections: CustomCollection[];
  publisher: Publisher;
  category: Category;
  collection: Collection;

  constructor() {

  }
}
