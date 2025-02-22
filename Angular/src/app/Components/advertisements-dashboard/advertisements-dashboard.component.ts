import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { AdvService } from '../../Services/adv.service';
import { Adv } from '../../Models/adv';
import { ButtonDeactivateAdvertisementComponent } from "../Buttons/button-deactivate-advertisement/button-deactivate-advertisement.component";

@Component({
  selector: 'app-advertisements-dashboard',
  standalone: true,
  imports: [CommonModule, NgxDatatableModule, ButtonDeactivateAdvertisementComponent],
  templateUrl: './advertisements-dashboard.component.html',
  styleUrl: './advertisements-dashboard.component.css'
})
export class AdvertisementsDashboardComponent implements OnInit {
  advertisements: Adv[] = [];
  selectedAdv: any | null = null;
  isModalOpen: boolean = false;

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

        console.log(this.advertisements)
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
}
