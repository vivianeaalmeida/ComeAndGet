import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Advertisement } from '../../Models/advertisement';
import { ButtonCreateResAttemptComponent } from '../Buttons/button-create-res-attempt/button-create-res-attempt.component';
import { MapComponent } from '../map/map.component';
import { NgFor, NgIf } from '@angular/common';
import { ReservationAttemptService } from '../../Services/reservation-attempt.service';
import { ReservationAttemptResponse } from '../../Models/reservation-attempt-response';
import { forkJoin, map, switchMap } from 'rxjs';
import { UsersService } from '../../Services/users.service';
import { ReservationAttemptWithUser } from '../../Models/reservation-attempt-with-user';

@Component({
  selector: 'app-advertisement-modal',
  standalone: true,
  imports: [ButtonCreateResAttemptComponent, MapComponent, NgIf, NgFor],
  templateUrl: './advertisement-modal.component.html',
  styleUrl: './advertisement-modal.component.css'
})
export class AdvertisementModalComponent implements OnInit {
  @Input() selectedAdv: Advertisement | null = null;
  @Output() modalClosed = new EventEmitter<void>();
  @Input() isLogged: any;
  @Input() showReservationAttempts:boolean = false;
  @Input() showReservationButton:boolean = true;
  reservations:ReservationAttemptWithUser[] = []

  constructor(private sanitizer: DomSanitizer, private resAttemptService:ReservationAttemptService, private userService:UsersService) {}
  
  ngOnInit(): void {
    if (this.showReservationAttempts) {
      this.resAttemptService
        .getReservationAttemptsByAdvertisementId(this.selectedAdv?.id || '')
        .pipe(
          switchMap(attempts => {
            const requests = attempts.map(attempt =>
              this.userService.getUserById(attempt.clientId || '').pipe(
                map(user => ({ reservation: attempt , user }))
              )
            );
            return forkJoin(requests); 
          })
        )
        .subscribe(res => {
          this.reservations = res;
        });
    }
  }

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