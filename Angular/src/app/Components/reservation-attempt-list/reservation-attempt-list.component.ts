import { Component, Input, OnInit } from '@angular/core';
import { ReservationAttemptService } from '../../Services/reservation-attempt.service';
import { ReservationAttempt } from '../../Models/reservation-attempt';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../Services/auth.service';
import { LoggedUserSession } from '../../Models/logged-user-session';
import { User1 } from '../../Models/user1';
import { ReservationAttemptResponse } from '../../Models/reservation-attempt-response';

@Component({
  selector: 'app-reservation-attempt-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reservation-attempt-list.component.html',
  styleUrl: './reservation-attempt-list.component.css'
})
export class ReservationAttemptListComponent implements OnInit {
  loggedUser?: User1 | null;

  reservationAttempt:ReservationAttemptResponse[] = [];
  

  ngOnInit(): void {
    this.authService.getUser().subscribe((user) => {
      this.loggedUser = user;
      console.log(user)
      this.reservationAttemptService
      .getUserRequestAttempts(user?.userId || '')
      .subscribe(res => this.reservationAttempt = res)
    });
  }

  constructor(private reservationAttemptService:ReservationAttemptService, private authService:AuthService){}



}

/*

  adv!: Advertisement;

  constructor(
    private resAttemptService: ResAttemptService,
    private advService: AdvService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    );
*/