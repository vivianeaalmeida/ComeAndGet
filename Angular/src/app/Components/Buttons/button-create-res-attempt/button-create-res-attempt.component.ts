import { Component, Input, OnInit } from '@angular/core';
import { ResAttemptService } from '../../../Services/res-attempt.service';
import Swal from 'sweetalert2';
import { AuthService } from '../../../Services/auth.service';
import { map } from 'rxjs';
import { Router } from '@angular/router';
import { AdvService } from '../../../Services/adv.service';
import { Advertisement } from '../../../Models/advertisement';
import { ReservationAttempt } from '../../../Models/reservation-attempt';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-button-create-res-attempt',
  standalone: true,
  imports: [NgIf],
  templateUrl: './button-create-res-attempt.component.html',
  styleUrl: './button-create-res-attempt.component.css',
})
export class ButtonCreateResAttemptComponent implements OnInit {
  @Input() advertisementId!: string;
  isLogged: any;
  adv!: Advertisement;

  constructor(
    private resAttemptService: ResAttemptService,
    private advService: AdvService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.user.pipe(map((user) => user)).subscribe((user) => {
      this.isLogged = user;
    });

    // Fetch the advertisement by id
    if (this.advertisementId) {
      console.log('Advertisement ID:', this.advertisementId);
      this.advService.getById(this.advertisementId).subscribe({
        next: (data) => {
          this.adv = data;
          console.log('Fetched advertisement:', data);
        },
        error: (error) => {
          console.error('Error getting the advertisement:', error);
        },
      });
    } else {
      console.error('Error: Unknown id.');
    }
  }

  addResAttempt() {
    if (!this.adv || !this.adv.id) {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Advertisement data is missing.',
        confirmButtonText: 'Ok',
      });
      return;
    }

    if (!this.isLogged) {
      this.router.navigate(['login']);
      return;
    }

    Swal.fire({
      icon: 'question',
      title: 'Add reservation attempt',
      text: 'Are you sure you want to add your reservation attempt to this donation?',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        const newResAttempt = {
          status: 'PENDING',
          advertisementId: this.adv.id!, 
        };

        console.log('Reservation attempt to be sent:', newResAttempt);

        // Send the reservation attempt to the backend
        this.resAttemptService.addResAttempt(newResAttempt).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Reservation attempt added successfully.',
              confirmButtonText: 'Ok',
            });
            console.log('Reservation attempt created:', newResAttempt);
          },
          error: (err) => {
            console.error('Error adding reservation attempt:', err);
            Swal.fire({
              icon: 'error',
              title: 'It was not possible to add the reservation attempt.',
              text: `${err?.error?.message || 'Unknown error'}`,
              confirmButtonText: 'Ok',
            });
          },
        });
      }
    });
  }

  redirect() {
    if (this.isLogged != null && this.isLogged.roles === 'User') {
      console.log('Estou logado!');
    } else {
      this.router.navigate(['login']);
    }
  }
}