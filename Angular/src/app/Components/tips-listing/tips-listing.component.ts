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
    this.profileInfo();
  }

  profileInfo() {
    this.userServ.getUser().subscribe((user) => {
      this.user = user;
      this.userId = user.userId;
      this.loadTips();
    });
  }

  loadTips(): void {
    this.isLoading = true;
    this.tipService.getTips().subscribe(
      (data: Tip[]) => {
        this.tips = data;
        this.totalPages = Math.ceil(this.tips.length / this.itemsPerPage);
        this.updatePage(this.currentPage);
        this.loadUserInteractions(); // Carregar interações após carregar as dicas
        this.isLoading = false;
      },
      (error) => {
        console.error('Error fetching tips', error);
        this.isLoading = false;
      }
    );
  }

  loadUserInteractions(): void {
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
  }

  updateTipsWithInteractions(): void {
    this.tips.forEach((tip) => {
      const interaction = this.userInteractions.find((i) => i.tipId === tip.id);
      tip.interactionId = interaction?.id || undefined;
      tip.hasLiked = interaction?.like || false;
      tip.hasFavorited = interaction?.favorite || false;
    });
  }

  toggleLike(tip: Tip): void {
    if (tip.interactionId) {
      this.updateInteraction(tip, {
        like: !tip.hasLiked,
        favorite: tip.hasFavorited,
      });
    } else {
      this.createInteraction(tip, { like: true, favorite: false });
    }
  }

  toggleFavorite(tip: Tip): void {
    if (tip.interactionId) {
      this.updateInteraction(tip, {
        like: tip.hasLiked,
        favorite: !tip.hasFavorited,
      });
    } else {
      this.createInteraction(tip, { like: false, favorite: true });
    }
  }

  createInteraction(tip: Tip, interaction: Partial<Interaction>): void {
    const newInteraction: Interaction = {
      tipId: tip.id,
      userId: this.userId!,
      ...interaction,
    };
    this.interactServ.addNewInteraction(newInteraction).subscribe(
      (createdInteraction) => {
        tip.interactionId = createdInteraction.id;
        tip.hasLiked = createdInteraction.like;
        tip.hasFavorited = createdInteraction.favorite;

        // Atualizar contadores corretamente
        if (createdInteraction.like) {
          tip.likeCount = (tip.likeCount || 0) + 1;
        }
        if (createdInteraction.favorite) {
          tip.favoriteCount = (tip.favoriteCount || 0) + 1;
        }

        this.userInteractions.push(createdInteraction);
      },
      (error) => {
        console.error('Error creating interaction', error);
      }
    );
  }

  updateInteraction(tip: Tip, interaction: Partial<Interaction>): void {
    if (!tip.interactionId) return;

    const wasLiked = tip.hasLiked;
    const wasFavorited = tip.hasFavorited;

    const updatedInteraction: Interaction = {
      tipId: tip.id,
      userId: this.userId!,
      ...interaction,
    };

    this.interactServ
      .updateInteraction(tip.interactionId, updatedInteraction)
      .subscribe(
        (updated) => {
          // Atualizar estado do like e favorito
          tip.hasLiked = updated.like;
          tip.hasFavorited = updated.favorite;

          // Atualizar contador de likes corretamente
          if (wasLiked !== updated.like) {
            tip.likeCount = updated.like
              ? (tip.likeCount || 0) + 1
              : Math.max(0, (tip.likeCount || 0) - 1);
          }

          // Atualizar contador de favoritos corretamente
          if (wasFavorited !== updated.favorite) {
            tip.favoriteCount = updated.favorite
              ? (tip.favoriteCount || 0) + 1
              : Math.max(0, (tip.favoriteCount || 0) - 1);
          }
        },
        (error) => {
          console.error('❌ Error updating interaction:', error);
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

  toggleContent(tip: Tip): void {
    this.openStates[tip.id] = !this.openStates[tip.id];
  }

  // refreshTipData(updatedTip: Tip): void {
  //   const index = this.tips.findIndex((t) => t.id === updatedTip.id);
  //   if (index !== -1) {
  //     this.tips[index] = updatedTip;
  //     this.updatePage(this.currentPage); // Atualiza a página com os dados novos
  //   }
  // }
}
