// button-update-adv.component.ts
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdvService } from '../../../Services/adv.service';
import { UpdateAdvModalComponent } from '../../update-adv-modal/update-adv-modal.component';
import { Advertisement } from '../../../Models/advertisement';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-button-update-adv',
  standalone: true,
  imports: [],
  templateUrl: './button-update-adv.component.html',
  styleUrl: './button-update-adv.component.css',
})
export class ButtonUpdateAdvComponent implements OnInit {
  @Input() id!: string;
  advertisement: Advertisement | null = null;

  constructor(private dialog: MatDialog, private advService: AdvService) {}

  ngOnInit(): void {
    if (this.id) {
      this.advService.getById(this.id).subscribe({
        next: (data) => {
          this.advertisement = data;
        },
        error: (err) => {
          console.error('Erro ao carregar o anúncio', err);
        },
      });
    } else {
      console.error('ID do anúncio não fornecido');
    }
  }

  openUpdateAdvModal() {
    if (this.advertisement) {
      const dialogRef = this.dialog.open(UpdateAdvModalComponent, {
        width: '40vw',
        disableClose: true,
        data: {
          id: this.id,
          title: this.advertisement.title,
          description: this.advertisement.description,
          status: this.advertisement.status,
        },
      });
  
      dialogRef.afterClosed().subscribe((result) => {
        if (result && result.data) {
          const updatedAdvertisement = {
            ...result.data,
            id: this.id,
          };
  
          this.advService.updateAdvertisement(this.id, updatedAdvertisement).subscribe({
            next: () => {
              Swal.fire({
                title: 'Success!',
                text: 'Advertisement updated successfully.',
                icon: 'success',
                confirmButtonText: 'OK',
              }).then(() => {
                console.log('Advertisement updated successfully');
              });
            },
            error: (err) => {
              console.error('Error updating advertisement', err);
              Swal.fire({
                title: 'Error!',
                text: `${err?.error.message}`,
                icon: 'error',
                confirmButtonText: 'OK',
              });
            },
          });
        }
      });
    } else {
      console.error('Advertisement not loaded');
    }
  }
}
