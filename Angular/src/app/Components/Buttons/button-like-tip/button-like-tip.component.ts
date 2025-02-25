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
  userInteractions: Interaction[] = [];

  @Input() tip!: Tip;

  constructor(
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
}
