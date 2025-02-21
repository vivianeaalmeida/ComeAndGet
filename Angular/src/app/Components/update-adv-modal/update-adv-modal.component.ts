import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-adv-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './update-adv-modal.component.html',
  styleUrl: './update-adv-modal.component.css',
})
export class UpdateAdvModalComponent {
  updateAdvForm: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<UpdateAdvModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.updateAdvForm = new FormGroup({
      title: new FormControl(data?.title || '', [
        Validators.required,
        Validators.minLength(5),
      ]),
      description: new FormControl(data?.description || '', [
        Validators.required,
        Validators.minLength(10),
      ]),
      status: new FormControl(data?.status || 'ACTIVE', [Validators.required]),
    });
  }

  close(result: boolean = false) {
    this.dialogRef.close(result);
  }

  onSubmit() {
    if (this.updateAdvForm.valid) {
      // Exibe um alerta de sucesso
      Swal.fire({
        title: 'Success!',
        text: 'Advertisement updated succesfully.',
        icon: 'success',
        confirmButtonText: 'OK',
      }).then(() => {
        this.dialogRef.close(this.updateAdvForm.value);
      });
    } else {
      Swal.fire({
        title: 'Erro!',
        text: 'Please, fill in all the required data properly.',
        icon: 'error',
        confirmButtonText: 'OK',
      });
    }
  }
}