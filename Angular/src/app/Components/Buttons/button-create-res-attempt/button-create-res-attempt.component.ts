import { Component, Input } from '@angular/core';
import { ReservationAttempt } from '../../../Models/reservation-attempt';
import { ResAttemptService } from '../../../Services/res-attempt.service';
import Swal from 'sweetalert2';
import { AuthService } from '../../../Services/auth.service';
import { map } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-button-create-res-attempt',
  standalone: true,
  imports: [],

  templateUrl: './button-create-res-attempt.component.html',
  styleUrl: './button-create-res-attempt.component.css',
})
export class ButtonCreateResAttemptComponent {
  @Input() id!: string;
  resAttempt!: ReservationAttempt;
  isLogged: any;

  constructor(
    private resAttemptService: ResAttemptService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.user.pipe(map((user) => user)).subscribe((user) => {
      this.isLogged = user;
    });

    if (this.id) {
      this.resAttemptService.getResAttempt(this.id).subscribe({
        next: (data) => {
          this.resAttempt = data;
          console.log('Fetched reservation attempt:', data);
        },
        error: (error) => {
          console.error('Error getting the reservation attempt:', error);
        },
      });
    } else {
      console.error('Error: Unknown id.');
    }
  }

  addResAttempt() {
    Swal.fire({
      icon: 'question',
      title: 'Add reservation attempt',
      text: 'Are you sure you want to add your reservation attempt to this donation?',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        this.resAttemptService.addResAttempt(this.resAttempt).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Reservation attempt added successfully.',
              confirmButtonText: 'Ok',
            });

            console.log(this.resAttempt);
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
