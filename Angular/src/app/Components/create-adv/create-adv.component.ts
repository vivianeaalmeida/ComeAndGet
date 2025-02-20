import { Component, Input, OnInit } from '@angular/core';
import { ButtonComponent } from '../button/button.component';
import { AdvService } from '../../Services/adv.service';
import Swal from 'sweetalert2';
import { HttpClientModule } from '@angular/common/http';
import {
  FormGroup,
  ReactiveFormsModule,
  FormControl,
  Validators,
} from '@angular/forms';
import { Category } from '../../Models/category';
import { Advertisement } from '../../Models/advertisement';
import { NgFor, NgIf } from '@angular/common';
import { CategoryService } from '../../Services/category.service';
import { MunicipalityService } from '../../Services/municipality.service';

@Component({
  selector: 'app-create-adv',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgFor],
  templateUrl: './create-adv.component.html',
  styleUrl: './create-adv.component.css',
})
export class CreateAdvComponent implements OnInit {
  buttonText: string = 'Submit';
  imagem!: File | string;
  formData: FormGroup;
  submitted = false;
  allCategories: Category[] = [];
  allMunicipalities: string[] = []; // Corrigido para 'string[]'

  constructor(
    private adServ: AdvService,
    private categServ: CategoryService,
    private municipalitiesService: MunicipalityService
  ) {
    this.formData = new FormGroup({
      title: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
      ]),
      description: new FormControl('', [
        Validators.required,
        Validators.minLength(20),
      ]),
      municipality: new FormControl('', [Validators.required]),
      condition: new FormControl('', [Validators.required]),
      category: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
    this.getAllCategories();
    this.getAllMunicipalities();
  }

  get title() {
    return this.formData.get('title');
  }

  get description() {
    return this.formData.get('description');
  }

  get municipality() {
    return this.formData.get('municipality');
  }

  get condition() {
    return this.formData.get('condition');
  }

  get category() {
    return this.formData.get('category');
  }

  onFileSelected(event: any) {
    this.imagem = event.target.files[0];
  }

  submitAdv() {
    console.log('Entrou no submitAdv');
    this.submitted = true;

    console.log(this.formData.value.category);
    console.log('Form Values:', this.formData.value);
    console.log('Form Valid:', this.formData.valid);

    if (this.formData.valid) {
      const formValue = this.formData.value;
      console.log('Está válido');

      const advertisementDTO = {
        title: formValue.title,
        description: formValue.description,
        municipality: formValue.municipality,
        item: {
          condition: formValue.condition,
          category: { id: formValue.category },
        },
      };

      const formData = new FormData();
      formData.append(
        'advertisementDTO',
        new Blob([JSON.stringify(advertisementDTO)], {
          type: 'application/json',
        })
      );

      if (this.imagem) {
        formData.append('imageFile', this.imagem);
        console.log('Imagem selecionada:', this.imagem);
      }

      this.adServ.add('advertisements', formData).subscribe({
        next: () =>
          Swal.fire({ icon: 'success', title: 'Ad added successfully!' }),
        error: () => Swal.fire({ icon: 'error', title: 'Error adding ad!' }),
      });
    }
  }

  getAllCategories() {
    this.categServ.getCategories().subscribe({
      next: (resp) => {
        this.allCategories = resp;
      },
      error: (err) => {
        console.error('Erro ao buscar categorias:', err);
      },
    });
  }

  getAllMunicipalities() {
    this.municipalitiesService.getMunicipalities().subscribe({
      next: (resp) => {
        this.allMunicipalities = resp;
      },
      error: (err) => {
        console.error('Erro ao buscar municípios:', err);
      },
    });
  }
}
