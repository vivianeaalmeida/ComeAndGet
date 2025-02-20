import { Item } from "./item";

export interface Advertisement {
  title: string;
  description: string;
  date: string;
  municipality: string;
  status: string;
  item: Item;
  clientId: string;
}
