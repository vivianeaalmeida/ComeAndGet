import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../Services/category.service';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-categorieslisting',
  standalone: true,
  imports: [NgxDatatableModule, ReactiveFormsModule],
  templateUrl: './categorieslisting.component.html',
  styleUrl: './categorieslisting.component.css',
})
export class CategorieslistingComponent implements OnInit {
  categories: any[] = [];
  hasCategories: boolean = false;

  constructor(private categoryServ: CategoryService) {}

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this.categoryServ.getCategories().subscribe({
      next: (response) => {
        console.log(response);
        this.categories = response;
        this.hasCategories = this.categories.length > 0;
      },
      error: (error) => {
        console.error('Error fetching categories', error);
      },
    });
  }
}
