import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Advertisement } from '../../../Models/advertisement';
import { AdvService } from '../../../Services/adv.service';
import Swal from 'sweetalert2';
import { UserAreaComponent } from '../../user-area/user-area.component';

@Component({
  selector: 'app-button-deactivate-advertisement',
  standalone: true,
  imports: [],
  templateUrl: './button-deactivate-advertisement.component.html',
  styleUrl: './button-deactivate-advertisement.component.css',
})
export class ButtonDeactivateAdvertisementComponent implements OnInit {
  @Input() id!: string;
  @Output() advertisementDeactivated = new EventEmitter<void>(); // Evento de output
  advertisement!: Advertisement;

  constructor(private advService: AdvService, private userArea: UserAreaComponent) {}

  ngOnInit(): void {
    if (this.id) {
      this.advService.getById(this.id).subscribe({
        next: (data) => {
          this.advertisement = data;
        },
        error: (err) => {
          console.error('Erro ao obter o anúncio:', err);
        },
      });
    } else {
      console.error('ID não fornecido');
    }
  }

  deactivateAdv() {
    Swal.fire({
      icon: 'question',
      title: 'Cancel advertisement',
      text: 'Are you sure you want to cancel this advertisement?',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        this.advService.deactivateAdvertisement(this.id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Advertisement canceled',
              confirmButtonText: 'Ok',
            });

            console.log('Event emitted: advertisementDeactivated'); // Log de depuração
            this.userArea.asyncGetMyAdvs();
          },
          error: (err) => {
            console.error('Error canceling advertisement:', err);
            Swal.fire({
              icon: 'error',
              title: 'It was not possible to cancel the advertisement',
              confirmButtonText: 'Ok',
            });
          },
        });
      }
    });
  }
}
