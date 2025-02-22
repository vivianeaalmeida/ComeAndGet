import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { AdvService } from '../../Services/adv.service';
import { Adv } from '../../Models/adv';
import { ButtonDeactivateAdvertisementComponent } from "../Buttons/button-deactivate-advertisement/button-deactivate-advertisement.component";
import { AdvertisementModalComponent } from '../advertisement-modal/advertisement-modal.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-advertisements-dashboard',
  standalone: true,
  imports: [CommonModule, NgxDatatableModule, AdvertisementModalComponent],
  templateUrl: './advertisements-dashboard.component.html',
  styleUrl: './advertisements-dashboard.component.css'
})
export class AdvertisementsDashboardComponent implements OnInit {
  advertisements: Adv[] = []; // Lista completa de anúncios
  filteredAdvertisements: Adv[] = []; // Lista filtrada de anúncios
  selectedAdv: any | null = null;
  isModalOpen: boolean = false;
  isLogged: any;

  columns = [
    { name: 'Title', prop: 'title', width: 100 },
    { name: 'Status', prop: 'status', width: 50 }
  ];

  constructor(private advService: AdvService) { }

  ngOnInit(): void {
    this.getAdvertisements();
  }

  getAdvertisements(): void {
    this.advService.get('advertisements').subscribe({
      next: (response) => {
        this.advertisements = response;
        this.filteredAdvertisements = response; // Inicialmente, exibe todos os anúncios
        console.log(this.advertisements);
      },
      error: (error) => {
        console.error('Error fetching advs', error);
      },
    });
  }

  getClosedAdvertisements(): void {
    this.advService.get('advertisements/closed').subscribe({
      next: (response) => {
        this.advertisements = response;
        this.filteredAdvertisements = response;
        console.log(this.advertisements);
      },
      error: (error) => {
        console.error('Error fetching advs', error);
      },
    });
  }

  openModal(adv: Adv) {
    this.selectedAdv = adv;
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
    this.selectedAdv = null;
  }

  closeModalOut(event: Event) {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }

  deactivateAdv(id: string) {
    Swal.fire({
      icon: 'question',
      title: 'Cancel advertisement',
      text: 'Are you sure you want to cancel this advertisement?',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
    }).then((result) => {
      if (result.isConfirmed) {
        this.advService.deactivateAdvertisement(id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Advertisement canceled',
              confirmButtonText: 'Ok',
            });

            this.getAdvertisements();
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

  // Método para filtrar anúncios com base no status selecionado
  filterAdvertisements(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const status = selectElement.value;

    if (status === 'ALL') {
      this.filteredAdvertisements = this.advertisements; // Exibe todos os anúncios
    } else if (status === 'ACTIVE') {
      this.filteredAdvertisements = this.advertisements.filter(adv => adv.status === 'ACTIVE');
    } else if (status === 'CLOSED') {
      this.filteredAdvertisements = this.advertisements.filter(adv => adv.status === 'CLOSED');
    }
  }
}