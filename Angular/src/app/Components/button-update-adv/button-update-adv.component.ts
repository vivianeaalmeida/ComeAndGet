// button-update-adv.component.ts
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdvService } from '../../Services/adv.service';
import { UpdateAdvModalComponent } from '../update-adv-modal/update-adv-modal.component';
import { Adv } from '../../Models/adv'; // Importe o tipo Adv corretamente
import { Advertisement } from '../../Models/advertisement';

@Component({
  selector: 'app-button-update-adv',
  standalone: true,
  imports: [],
  templateUrl: './button-update-adv.component.html',
  styleUrl: './button-update-adv.component.css',
})
export class ButtonUpdateAdvComponent implements OnInit {
  @Input() id!: string;
  advertisement: Advertisement | null = null; // Usando null ao invés de valores vazios

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

      dialogRef.afterClosed().subscribe((updatedData) => {
        console.log('Dados atualizados:', updatedData);
        if (updatedData) {
          // Enviar o ID junto com os dados da atualização
          const updatedAdvertisement = {
            ...updatedData,
            id: this.id, // Garantir que o id seja enviado corretamente
          };
          this.advService
            .updateAdvertisement(this.id, updatedAdvertisement)
            .subscribe({
              next: () => {
                console.log('Anúncio atualizado com sucesso');
              },
              error: (err) => {
                console.error('Erro ao atualizar o anúncio', err);
              },
            });
        }
      });
    } else {
      console.error('Anúncio não carregado');
    }
  }
}