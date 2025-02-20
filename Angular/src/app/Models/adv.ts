import { Item } from './item';

export interface Adv {
  title: string;
  description: string;
  date: string;
  municipality: string;
  status: string;
  item: Item;
  clientId: string;
}
