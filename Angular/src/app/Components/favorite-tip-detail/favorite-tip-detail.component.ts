import { Component, NgModule, OnInit } from '@angular/core';
import { User1 } from '../../Models/user1';
import { Tip } from '../../Models/tip';
import { TipService } from '../../Services/tip.service';
import { AuthService } from '../../Services/auth.service';
import { NgFor } from '@angular/common';
import { ButtonFavoriteTipComponent } from '../Buttons/button-favorite-tip/button-favorite-tip.component';
import { InteractionsService } from '../../Services/interactions.service';
import { Interaction } from '../../Models/interaction';

@Component({
  selector: 'app-favorite-tip-detail',
  standalone: true,
  imports: [NgFor, ButtonFavoriteTipComponent],
  templateUrl: './favorite-tip-detail.component.html',
  styleUrl: './favorite-tip-detail.component.css',
})
export class FavoriteTipDetailComponent implements OnInit {
  tips: Tip[] = [];
  loggedUser?: User1;
  userInteractions: Interaction[] = [];
  userId?: string;

  constructor(
    private tipService: TipService,
    private authService: AuthService,
    private interactServ: InteractionsService
  ) {}

  ngOnInit() {
    this.awaitUserInfo();
  }

  async awaitUserInfo() {
    await this.loadUserInfo();
    await this.loadTips();
    console.log(this.userId);
    console.log(this.loggedUser);

    this.loadUserInteractions();
  }

  loadTips(): Promise<any> {
    return new Promise((resolve) => {
      this.tipService
        .getFavoriteTipsByUserId(this.userId || '')
        .subscribe((res) => {
          this.tips = res;
          resolve(true);
        });
    });
  }

  loadUserInfo(): Promise<any> {
    return new Promise((resolve) => {
      this.authService.getUser().subscribe((user) => {
        this.loggedUser = user;
        this.userId = user.userId;
        resolve(true);
      });
    });
  }

  loadUserInteractions(): void {
    console.log(this.loggedUser);
    if (!this.loggedUser?.userId) return;
    this.interactServ.getInteractionsByUser(this.loggedUser?.userId).subscribe(
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
    console.log(this.tips);
  }

  refreshTipData(updatedTip: Tip): void {
    const index = this.tips.findIndex((t) => t.id === updatedTip.id);
    if (index !== -1) {
      this.tips[index] = updatedTip;
      //this.updatePage(this.currentPage); // Atualiza a página com os dados novos
    }
  }
}
