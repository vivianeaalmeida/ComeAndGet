import { Component, Input, OnInit } from '@angular/core';
import { ReservationAttemptService } from '../../Services/reservation-attempt.service';
import { ReservationAttempt } from '../../Models/reservation-attempt';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reservation-attempt-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reservation-attempt-list.component.html',
  styleUrl: './reservation-attempt-list.component.css'
})
export class ReservationAttemptListComponent implements OnInit {

  reservationAttempt:ReservationAttempt[] = [];
  

  ngOnInit(): void {
    this.reservationAttemptService.getReservationAttemptsByAdvertisement("").subscribe(res => this.reservationAttempt = res)
  }

  constructor(private reservationAttemptService:ReservationAttemptService){}



}
