import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Advertisement } from '../../Models/advertisement';
import { ButtonCreateResAttemptComponent } from '../Buttons/button-create-res-attempt/button-create-res-attempt.component';
import { MapComponent } from '../map/map.component';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ReservationAttemptService } from '../../Services/reservation-attempt.service';
import { ReservationAttemptResponse } from '../../Models/reservation-attempt-response';
import { BehaviorSubject, forkJoin, map, switchMap } from 'rxjs';
import { UsersService } from '../../Services/users.service';
import { ReservationAttemptWithUser } from '../../Models/reservation-attempt-with-user';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-advertisement-modal',
  standalone: true,
  imports: [ButtonCreateResAttemptComponent, MapComponent, NgIf, NgFor, CommonModule],
  templateUrl: './advertisement-modal.component.html',
  styleUrl: './advertisement-modal.component.css'
})
export class AdvertisementModalComponent implements OnInit {
  @Input() selectedAdv: Advertisement | null = null;
  @Output() modalClosed = new EventEmitter<void>();
  @Input() isLogged: any;
  @Input() showReservationAttempts:boolean = false;
  @Input() showReservationButton:boolean = true;
  private reservationsSubject = new BehaviorSubject<ReservationAttemptWithUser[]>([]);
  public reservations$ = this.reservationsSubject.asObservable();

  constructor(private sanitizer: DomSanitizer, private resAttemptService:ReservationAttemptService, private userService:UsersService) {}
  
  ngOnInit(): void {
    this.getReservationAttempts();
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

  onAccepted(id:string){
   this.onChangeStatus(id,'ACCEPTED')
  }

  onRejected(id:string){
    this.onChangeStatus(id,'REJECTED')
  }

  onChangeStatus(id:string, status:string){
    this.resAttemptService.updateReservationAttemptStatus(id, status)
    .subscribe(
    {
      next: _ => {
        this.updateReservationStatus(id, status)
        this.showSuccessMessage()
      },
      error: e => {console.log(e);this.showErrorMessage(e.error.message)}
    }
    )
  }

  showSuccessMessage(){
     Swal.fire({
            title: 'Success!',
            text: 'Request updated',
            icon: 'success',
            confirmButtonText: 'OK',
          });
  }

  showErrorMessage(error:string){
    Swal.fire({
           title: 'Error!',
           text: error,
           icon: 'error',
           confirmButtonText: 'OK',
         });
 }

 private updateReservationStatus(reservationId: string, newStatus: string) {
  const updatedReservations = this.reservationsSubject.getValue().map(item => {
    if (item.reservation.id === reservationId) {
      item.reservation.status = newStatus;
    } else if(newStatus == 'DONATED' && item.reservation.status !='CANCELED'){
      item.reservation.status = 'REJECTED';
    }
    return item;
  });
  this.reservationsSubject.next(updatedReservations);
}

 private getReservationAttempts() {
    if (this.showReservationAttempts) {
      this.resAttemptService
        .getReservationAttemptsByAdvertisementId(this.selectedAdv?.id || '')
        .pipe(
          switchMap(attempts => {
            const requests = attempts.map(attempt => this.userService.getUserById(attempt.clientId || '').pipe(
              map(user => ({ reservation: attempt, user }))
            )
            );
            return forkJoin(requests);
          })
        )
        .subscribe(res => {
          this.reservationsSubject.next(res);
        });
    }
  }


}