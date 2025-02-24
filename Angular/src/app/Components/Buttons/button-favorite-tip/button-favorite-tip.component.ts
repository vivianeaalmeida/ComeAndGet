import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Tip } from '../../../Models/tip';
import { InteractionsService } from '../../../Services/interactions.service';
import { Interaction } from '../../../Models/interaction';
import { AuthService } from '../../../Services/auth.service';
import { User1 } from '../../../Models/user1';

// button-favorite-tip.component.ts
@Component({
  selector: 'app-button-favorite-tip',
  standalone: true,
  template: `
    <button (click)="toggleFavorite()">
      <i
        [class.fa-solid]="tip?.hasFavorited"
        [class.fa-regular]="!tip?.hasFavorited"
        class="fa-bookmark fa-lg text-black text-md"
      ></i>
    </button>
  `,
})
export class ButtonFavoriteTipComponent {
  user?: User1;
  userId?: string;

  @Input() tip!: Tip;
  @Output() interactionChanged = new EventEmitter<void>();

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

  toggleFavorite(): void {
    if (!this.tip) return;
    const updatedInteraction: Interaction = {
      tipId: this.tip.id,
      userId: this.userId!,
      like: this.tip.hasLiked,
      favorite: !this.tip.hasFavorited,
    };

    this.interactServ
      .updateInteraction(this.tip.interactionId!, updatedInteraction)
      .subscribe(() => {
        this.tip.hasFavorited = !this.tip.hasFavorited;
        this.tip.favoriteCount =
          (this.tip.favoriteCount || 0) + (this.tip.hasFavorited ? 1 : -1);
        this.interactionChanged.emit();
      });
  }
}
