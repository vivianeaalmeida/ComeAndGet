import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AdvertisementCardComponent } from '../advertisement-card/advertisement-card.component';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, AdvertisementCardComponent, ButtonComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {}
