import { Component, Input } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-advertisement-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './advertisement-card.component.html',
  styleUrl: './advertisement-card.component.css',
})
export class AdvertisementCardComponent {
  @Input() imageSrc: string = '';
  @Input() title: string = 'Default Title';
  @Input() description: string = 'Default description text goes here.';
  @Input() buttonText: string = 'See More';
}
