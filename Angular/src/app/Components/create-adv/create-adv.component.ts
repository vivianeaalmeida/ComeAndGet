import { Component } from '@angular/core';
import { ButtonComponent } from '../button/button.component';
import { AdvServiceService } from '../../Services/adv-service.service';
import Swal from 'sweetalert2';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-create-adv',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './create-adv.component.html',
  styleUrl: './create-adv.component.css',
})
export class CreateAdvComponent {
  buttonText: string = 'EnzoGabriel';
  imagem!: File | string;

  constructor(private adServ: AdvServiceService) {}

  submit() {
    const formData = new FormData();

    const advertisementDTO = {
      title: 'Exemplo',
      description: 'desc exemplo',
      municipality: 'Porto',
      item: {
        condition: 'GOOD',
        image: '',
        category: { id: '1' },
      },
      clientId: '1',
    };

    formData.append(
      'advertisementDTO',
      new Blob([JSON.stringify(advertisementDTO)], { type: 'application/json' })
    );
    formData.append('imageFile', this.imagem);

    console.log(formData.get('imageFile'));

    this.adServ.add('advertisements', formData).subscribe({
      next: (response) => {
        Swal.fire({
          icon: 'success',
          title: 'Adv added successfully!.',
        });
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Error adding this adv!',
        });
      },
    });
  }

  onFileSelected(event: any) {
    this.imagem = event.target.files[0];
  }
}
