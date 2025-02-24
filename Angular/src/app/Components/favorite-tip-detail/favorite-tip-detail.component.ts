import { Component, OnInit } from '@angular/core';
import { User1 } from '../../Models/user1';
import { Tip } from '../../Models/tip';
import { TipService } from '../../Services/tip.service';
import { AuthService } from '../../Services/auth.service';
import { NgFor } from '@angular/common';
import { ButtonLikeTipComponent } from '../Buttons/button-like-tip/button-like-tip.component';
import { ButtonFavoriteTipComponent } from '../Buttons/button-favorite-tip/button-favorite-tip.component';

@Component({
  selector: 'app-favorite-tip-detail',
  standalone: true,
  imports: [NgFor, ButtonLikeTipComponent, ButtonFavoriteTipComponent],
  templateUrl: './favorite-tip-detail.component.html',
  styleUrl: './favorite-tip-detail.component.css'
})
export class FavoriteTipDetailComponent implements OnInit {
  tips: Tip[] = [];
  loggedUser?: User1 | null;

  constructor(private tipService: TipService, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.getUser().subscribe((user) => {
      this.loggedUser = user;
      console.log(user);
      this.tipService
      .getFavoriteTipsByUserId(user?.userId || '')
      .subscribe(res => this.tips = res)
    });
  }
}