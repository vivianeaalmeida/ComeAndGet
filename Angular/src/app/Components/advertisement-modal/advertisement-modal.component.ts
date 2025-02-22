import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Advertisement } from '../../Models/advertisement';
import { ButtonCreateResAttemptComponent } from '../Buttons/button-create-res-attempt/button-create-res-attempt.component';
import { MapComponent } from '../map/map.component';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-advertisement-modal',
  standalone: true,
  imports: [ButtonCreateResAttemptComponent, MapComponent],
  templateUrl: './advertisement-modal.component.html',
  styleUrl: './advertisement-modal.component.css'
})
export class AdvertisementModalComponent {
  @Input() selectedAdv: Advertisement | null = null;
  @Output() modalClosed = new EventEmitter<void>();
  @Input() isLogged: any;

  constructor(private sanitizer: DomSanitizer) {}

  closeModal() {
    this.modalClosed.emit();
  }

  closeModalOut(event: Event) {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }

  getSafeImageUrl(imagePath: string | undefined): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(
      'http://localhost:8080/' + (imagePath || '')
    );
  }
}