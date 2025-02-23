import { Component, OnInit } from '@angular/core';
import { Tip } from '../../Models/tip';
import { TipService } from '../../Services/tip.service';
import { CommonModule } from '@angular/common';
import { ButtonLikeTipComponent } from '../Buttons/button-like-tip/button-like-tip.component';
import { ButtonFavoriteTipComponent } from '../Buttons/button-favorite-tip/button-favorite-tip.component';

@Component({
  selector: 'app-tips-listing',
  standalone: true,
  imports: [CommonModule, ButtonLikeTipComponent, ButtonFavoriteTipComponent],
  templateUrl: './tips-listing.component.html',
  styleUrl: './tips-listing.component.css'
})
export class TipsListingComponent implements OnInit {
  tips: Tip[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalPages: number = 1;
  currentPageTips: Tip[] = [];

  openStates: { [id: number]: boolean } = {};
  
  isLoading: boolean = false;

  constructor(private tipService: TipService) { }

  ngOnInit(): void {
    this.loadTips();
  }

  loadTips(): void {
    this.isLoading = true;
    this.tipService.getTips().subscribe(
      (data: Tip[]) => {
        this.tips = data;
        this.totalPages = Math.ceil(this.tips.length / this.itemsPerPage);
        this.updatePage(this.currentPage);
        this.isLoading = false;
      },
      error => {
        console.error('Error fetching tips', error);
        this.isLoading = false;
      }
    );
  }

  paginateTips(page: number): Tip[] {
    const start = (page - 1) * this.itemsPerPage;
    const end = start + this.itemsPerPage;
    return this.tips.slice(start, end);
  }

  changePage(page: number): void {
    if (page < 1 || page > this.totalPages) return;
    this.currentPage = page;
    this.updatePage(page);
  }

  updatePage(page: number): void {
    this.currentPageTips = this.paginateTips(page);
  }

  // Alternates the open state of a tip
  toggleContent(tip: Tip): void {
    this.openStates[tip.id] = !this.openStates[tip.id];
  }
}