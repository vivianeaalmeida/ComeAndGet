import { Component } from '@angular/core';
import { Tip } from '../../Models/tip';
import { TipService } from '../../Services/tip.service';
import { AuthService } from '../../Services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-public-tips-listing',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './public-tips-listing.component.html',
  styleUrl: './public-tips-listing.component.css'
})
export class PublicTipsListingComponent {
  tips: Tip[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalPages: number = 1;
  currentPageTips: Tip[] = [];

  openStates: { [id: number]: boolean } = {};

  isLoading: boolean = false;

  constructor(
    private tipService: TipService,
    private userServ: AuthService
  ) {}

  ngOnInit(): void {
    this.loadTips();
  }

  loadTips(): Promise<any> {
    return new Promise((resolve) => {
      this.isLoading = true;
      this.tipService.getTips().subscribe((data: Tip[]) => {
        this.tips = data;
        this.totalPages = Math.ceil(this.tips.length / this.itemsPerPage);
        this.updatePage(this.currentPage);
        this.isLoading = false;
      });
      resolve(true);
    });
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

  toggleContent(tip: Tip): void {
    this.openStates[tip.id] = !this.openStates[tip.id];
  }
}
