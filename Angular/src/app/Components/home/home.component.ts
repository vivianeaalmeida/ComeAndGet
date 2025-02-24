import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AdvertisementCardComponent } from '../advertisement-card/advertisement-card.component';
import { ButtonComponent } from '../Buttons/button/button.component';
import { AdvService } from '../../Services/adv.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Advertisement } from '../../Models/advertisement';
import { NgFor, NgIf } from '@angular/common';
import { AdvertisementModalComponent } from '../advertisement-modal/advertisement-modal.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    AdvertisementCardComponent,
    NgFor,
    AdvertisementModalComponent,
    NgIf,
    ButtonComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  advCollection: Advertisement[] = [];
  selectedAdv: any | null = null;
  openedModal: boolean = false;

  constructor(private advServ: AdvService, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.loadAdvertisements();
  }

  loadAdvertisements(): void {
    this.advServ.get('advertisements/active').subscribe(
      (resp) => {
        this.advCollection = this.getRandomAdvertisements(resp, 4);
      },
      (error) => {
        console.error('Error fetching advertisements:', error);
      }
    );
  }

  getRandomAdvertisements(
    ads: Advertisement[],
    count: number
  ): Advertisement[] {
    return ads
      .map((ad) => ({ ad, sort: Math.random() }))
      .sort((a, b) => a.sort - b.sort)
      .map(({ ad }) => ad)
      .slice(0, count);
  }

  getSafeImageUrl(imagePath: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(
      'http://localhost:8080/' + imagePath
    );
  }

  openModal(adv: Advertisement) {
    console.log('Selected Advertisement:', adv);
    this.selectedAdv = adv;
    this.openedModal = true;
    console.log('Opened Modal:', this.openedModal);
  }

  closeModal() {
    console.log('Modal Fechado');
    this.openedModal = false;
    this.selectedAdv = null;
  }
}
