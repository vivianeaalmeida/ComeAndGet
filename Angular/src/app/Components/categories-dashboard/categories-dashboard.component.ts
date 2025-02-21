import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../Services/category.service';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Category } from '../../Models/category';
import Swal from 'sweetalert2';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-categories-dashboard',
  standalone: true,
  imports: [NgxDatatableModule, ReactiveFormsModule, NgIf],
  templateUrl: './categories-dashboard.component.html',
  styleUrl: './categories-dashboard.component.css',
})
export class CategoriesDashboardComponent {
  categories: Category[] = [];
  categoryForm!: FormGroup;
  isEditingOrCreating: string = '';
  isModalOpen: boolean = false;
  selectedCategory: any;
  categoryId?: string = '';

  constructor(private categoryServ: CategoryService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
    this.getCategories();
  }

  initForm(): void {
    this.categoryForm = this.fb.group({
      designation: ['', [Validators.required]],
    });
  }

  getCategories(): void {
    this.categoryServ.getCategories().subscribe({
      next: (response) => {
        this.categories = response;
      },
      error: (error) => {
        console.error('Error fetching categories', error);
      },
    });
  }

  createCategory(): void {
    this.categoryServ.addCategory(this.categoryForm.value).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Category added successfully!.',
        });
        this.closeModal();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error adding this category!',
        });
      },
    });
  }

  updateCategory(): void {
    // Criar uma cópia do formulário e adicionar o ID manualmente
    const categoryData = {
      ...this.categoryForm.value,
      id: this.categoryId,
    };

    this.categoryServ.updateCategory(this.categoryId!, categoryData).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Category updated successfully!.',
        });
        this.getCategories();
        this.closeModal();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error updating category!',
        });
      },
    });
  }

  deleteCategory(id: string): void {
    Swal.fire({
      icon: 'warning',
      titleText: 'Are you sure deleting this category?',
      showCancelButton: true,
      showConfirmButton: true,
    }).then((result) => {
      if (result.isConfirmed) {
        this.categoryServ.deleteCategory(id).subscribe({
          next: (response) => {
            this.categories = this.categories.filter(
              (category) => category.id !== id
            );
            Swal.fire({
              icon: 'success',
              title: 'Category deleted successfully!',
            });
          },
          error: (error) => {
            Swal.fire({
              icon: 'error',
              title: `Error deleting this category - ${error?.error?.message}`,
            });
          },
        });
      }
    });
  }

  fillCategoryForm(category: Category) {
    if (category != null) {
      this.categoryForm.get('designation')?.setValue(category.designation);
    }
  }

  createOrUpdate(isEditingOrCreating: string) {
    if (isEditingOrCreating === 'creating') {
      this.createCategory();
    } else if (isEditingOrCreating === 'editing') {
      this.updateCategory();
    }
  }

  openModal(isEditingOrCreating: string, category?: Category) {
    this.isEditingOrCreating = isEditingOrCreating;
    this.selectedCategory = category;
    this.categoryId = category?.id;
    this.isModalOpen = true;
    this.fillCategoryForm(this.selectedCategory);
  }

  closeModal() {
    this.isModalOpen = false;
    this.selectedCategory = null;
    this.categoryForm.get('designation')?.setValue('');
  }

  closeModalOut(event: Event) {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }
}
