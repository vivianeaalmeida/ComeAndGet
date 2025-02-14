import { Component } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-advertisement-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './advertisement-card.component.html',
  styleUrl: './advertisement-card.component.css',
})
export class AdvertisementCardComponent {}
