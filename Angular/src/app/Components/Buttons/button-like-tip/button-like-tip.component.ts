import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { Tip } from '../../../Models/tip';
import { InteractionsService } from '../../../Services/interactions.service';
import { Interaction } from '../../../Models/interaction';
import { AuthService } from '../../../Services/auth.service';
import { User1 } from '../../../Models/user1';

@Component({
  selector: 'app-button-like-tip',
  standalone: true,
  template: `
    <button (click)="toggleLike()">
      <i
        [class.fa-solid]="tip?.hasLiked"
        [class.fa-regular]="!tip?.hasLiked"
        class="fa-thumbs-up fa-lg text-black text-xl"
      ></i>
    </button>
  `,
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
    this.profileInfo();
  }

  profileInfo() {
    this.userServ.getUser().subscribe((user) => {
      this.user = user;
      this.userId = user.userId;
    });
  }

  toggleLike(): void {
    if (!this.tip) return;
    const updatedInteraction: Interaction = {
      tipId: this.tip.id,
      userId: this.userId!,
      like: !this.tip.hasLiked,
      favorite: this.tip.hasFavorited,
    };

    this.interactServ
      .updateInteraction(this.tip.interactionId!, updatedInteraction)
      .subscribe(() => {
        this.tip.hasLiked = !this.tip.hasLiked;
        this.tip.likeCount =
          (this.tip.likeCount || 0) + (this.tip.hasLiked ? 1 : -1);
        this.interactionChanged.emit();
      });
  }
}
