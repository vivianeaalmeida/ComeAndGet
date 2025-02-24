import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { Tip } from '../../Models/tip';
import { TipService } from '../../Services/tip.service';
import Swal from 'sweetalert2';
import { TipModalComponent } from "../tip-modal/tip-modal.component";

@Component({
  selector: 'app-tips-dashboard',
  standalone: true,
  imports: [NgxDatatableModule, ReactiveFormsModule, CommonModule, TipModalComponent],
  templateUrl: './tips-dashboard.component.html',
  styleUrl: './tips-dashboard.component.css'
})
export class TipsDashboardComponent implements OnInit {
  tips: Tip[] = [];
  selectedTip: any | null = null;
  tipId?: number;
  tipForm!: FormGroup;
  isEditingOrCreating: string = '';

  isModalOpen: boolean = false;
  isDetailModalOpen: boolean = false;
  isLogged: any;

  columns = [
    { name: 'Title', prop: 'title', width: 100 },
  ];

  constructor(private tipServ: TipService, private fb:FormBuilder) { }

  ngOnInit(): void {
    this.getTips();
    this.initForm();
  }

  initForm() {
    this.tipForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
  }

  getTips(): void {
    this.tipServ.getTips().subscribe({
      next: (response) => {
        this.tips = response;
      },
      error: (error) => {
        console.error('Error fetching categories', error);
      },
    });
  }

  createTip(): void {
    this.tipServ.addTip(this.tipForm.value).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Tip added successfully!.',
        });
        this.closeModal();
        this.getTips();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: `Error adding this tip! ${error?.error?.message}`,
        });
      },
    });
  }

  updateTip(): void {
    // Criar uma cópia do formulário e adicionar o ID manualmente
    const tipData = {
      ...this.tipForm.value,
      id: this.tipId,
    };

    console.log(tipData);

    this.tipServ.updateTip(this.tipId!, tipData).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Tip updated successfully!.',
        });
        this.getTips();
        this.closeModal();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: `Error updating tip! - ${error?.error?.message}`,
        });
      },
    });
  }

  deleteTip(id: number): void {
    Swal.fire({
      icon: 'warning',
      titleText: 'Are you sure deleting this tip?',
      showCancelButton: true,
      showConfirmButton: true,
    }).then((result) => {
      if (result.isConfirmed) {
        this.tipServ.deleteTip(id).subscribe({
          next: (response) => {
            this.tips = this.tips.filter(
              (tip) => tip.id !== id
            );
            Swal.fire({
              icon: 'success',
              title: 'Tip deleted successfully!',
            });
          },
          error: (error) => {
            Swal.fire({
              icon: 'error',
              title: `Error deleting this tip - ${error?.error?.message}`,
            });
          },
        });
      }
    });
  }

  fillTipForm(tip: Tip) {
    if (tip != null) {
      this.tipForm.get('title')?.setValue(tip.title);
      this.tipForm.get('content')?.setValue(tip.content);

    }
  }

  createOrUpdate(isEditingOrCreating: string) {
    if (isEditingOrCreating === 'creating') {
      this.createTip();
    } else if (isEditingOrCreating === 'editing') {
      this.updateTip();
    }
  }

  openModal(isEditingOrCreating: string, tip?: Tip) {
    this.isEditingOrCreating = isEditingOrCreating;
    this.selectedTip = tip;
    this.tipId = tip?.id;
    this.isModalOpen = true;
    this.fillTipForm(this.selectedTip);
  }

  closeModal() {
    this.isModalOpen = false;
    this.selectedTip = null;
    this.tipForm.reset();
  }

  closeModalOut(event: Event) {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }

  openDetailModal(tip: Tip) {
    this.selectedTip = tip;
    this.isDetailModalOpen = true;
  }

  closeDetailModal() {
    this.isDetailModalOpen = false;
    this.selectedTip = null;
  }
}
