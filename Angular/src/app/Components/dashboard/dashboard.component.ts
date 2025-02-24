import { Component } from '@angular/core';
import { Category } from '../../Models/category';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../../Services/category.service';
import Swal from 'sweetalert2';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  categories: Category[] = [];
  categoryForm!: FormGroup;
  isEditingOrCreating: string = '';
  isModalOpen: boolean = false;
  selectedCategory: any;
  categoryId: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {}

  advertisementsDash() {
    this.router.navigate(['dashboard/advertisements']);
  }

  categoriesDash() {
    this.router.navigate(['dashboard/categories']);
  }

  tipsDash() {
    this.router.navigate(['dashboard/tips']);
  }
}
