import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import Swal from 'sweetalert2';
import { UsersService } from '../../Services/users.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  registerForm: FormGroup;

  public uppercasePattern: RegExp = /[A-Z]/;
  public lowercasePattern: RegExp = /[a-z]/;
  public numberPattern: RegExp = /[0-9]/;
  public specialPattern: RegExp = /[^a-zA-Z0-9]/;

  constructor(
    private addsrv: UsersService,
    private myRouter: Router,
    private fb: FormBuilder
  ) {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      phoneNumber: ['', [Validators.minLength(9)]],
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).+$'),
        ],
      ],
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.registerNewUser();
    }
  }

  registerNewUser() {
    if (this.registerForm.valid) {
      this.addsrv.registerUser(this.registerForm.value).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'New user registered successfully!',
          });
          this.myRouter.navigate(['login']);
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error registering new user!',
            text: error?.error?.message || 'An unexpected error occurred. Please try again.',
          });
        }
      });
    }
  }

  loginUser(): void {
    this.myRouter.navigate(['login']);
  }
  
  isPasswordMissing(pattern: RegExp): boolean {
    const password = this.registerForm.get('password')?.value || '';
    return !pattern.test(password);
  }
}
