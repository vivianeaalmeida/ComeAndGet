import { Component, OnInit } from '@angular/core';
import { Tip } from '../../Models/tip';
import { TipService } from '../../Services/tip.service';
import { CommonModule } from '@angular/common';
import { ButtonLikeTipComponent } from '../Buttons/button-like-tip/button-like-tip.component';
import { ButtonFavoriteTipComponent } from '../Buttons/button-favorite-tip/button-favorite-tip.component';
import { InteractionsService } from '../../Services/interactions.service';
import { AuthService } from '../../Services/auth.service';
import { User1 } from '../../Models/user1';
import { Interaction } from '../../Models/interaction';

@Component({
  selector: 'app-tips-listing',
  standalone: true,
  imports: [CommonModule, ButtonLikeTipComponent, ButtonFavoriteTipComponent],
  templateUrl: './tips-listing.component.html',
  styleUrl: './tips-listing.component.css',
})
export class TipsListingComponent implements OnInit {
  userInteractions: Interaction[] = [];
  tips: Tip[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalPages: number = 1;
  currentPageTips: Tip[] = [];
  userId?: string;
  user?: User1;

  openStates: { [id: number]: boolean } = {};

  isLoading: boolean = false;

  constructor(
    private tipService: TipService,
    private interactServ: InteractionsService,
    private userServ: AuthService
  ) {}

  ngOnInit(): void {
    this.awaitUserInfo();
  }

  async awaitUserInfo() {
    await this.profileInfo();
    await this.loadTips();
    await this.loadUserInteractions();
  }

  profileInfo(): Promise<any> {
    return new Promise((resolve) => {
      this.userServ.getUser().subscribe((user) => {
        this.user = user;
        this.userId = user.userId;
        this.loadTips();
      });
      resolve(true);
    });
  }

  loadTips(): Promise<any> {
    return new Promise((resolve) => {
      this.isLoading = true;
      this.tipService.getTips().subscribe((data: Tip[]) => {
        this.tips = data;
        this.totalPages = Math.ceil(this.tips.length / this.itemsPerPage);
        this.updatePage(this.currentPage);
        this.loadUserInteractions(); // Carregar interações após carregar as dicas
        this.isLoading = false;
      });
      resolve(true);
    });
  }

  loadUserInteractions(): Promise<any> {
    return new Promise((resolve) => {
      if (!this.userId) return;
      this.interactServ.getInteractionsByUser(this.userId).subscribe(
        (interactions) => {
          this.userInteractions = interactions;
          this.updateTipsWithInteractions();
        },
        (error) => {
          console.warn('User has no interactions yet.');
          this.userInteractions = [];
        }
      );
      resolve(true);
    });
  }

  updateTipsWithInteractions(): void {
    this.tips.forEach((tip) => {
      const interaction = this.userInteractions.find((i) => i.tipId === tip.id);
      tip.interactionId = interaction?.id || undefined;
      tip.hasLiked = interaction?.like || false;
      tip.hasFavorited = interaction?.favorite || false;
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
