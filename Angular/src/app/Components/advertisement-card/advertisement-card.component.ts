import { Component, Input } from '@angular/core';
import { ButtonComponent } from '../Buttons/button/button.component';
import { SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-advertisement-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './advertisement-card.component.html',
  styleUrl: './advertisement-card.component.css',
})
export class AdvertisementCardComponent {
  @Input() imageSrc: SafeResourceUrl = '';
  @Input() title: string = 'Default Title';
  @Input() description: string = 'Default description text goes here.';
  @Input() buttonText: string = 'See More';
}
