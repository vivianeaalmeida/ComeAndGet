import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { Tip } from '../../../Models/tip';
import { InteractionsService } from '../../../Services/interactions.service';
import { Interaction } from '../../../Models/interaction';
import { AuthService } from '../../../Services/auth.service';
import { User1 } from '../../../Models/user1';

@Component({
  selector: 'app-button-like-tip',
  standalone: true,
  templateUrl: './button-like-tip.component.html',
  styleUrl: './button-like-tip.component.css',
})
export class ButtonLikeTipComponent implements OnInit {
  user?: User1;
  userId?: string;

  @Input() tip!: Tip;
  @Output() interactionChanged = new EventEmitter<void>();

  constructor(
    private interactServ: InteractionsService,
    private userServ: AuthService
  ) {}
  ngOnInit(): void {
    console.log(this.tip);
    this.profileInfo();
  }

  profileInfo() {
    this.userServ.getUser().subscribe((user) => {
      this.user = user;
      this.userId = user.userId;
      this.toggleLike(this.tip);
    });
  }

  toggleLike(tip: Tip): void {
    this.updateInteraction(tip, {
      like: !tip.hasLiked,
      favorite: tip.hasFavorited,
    });
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
          console.error(' Error updating interaction:', error);
        }
      );
  }
}
