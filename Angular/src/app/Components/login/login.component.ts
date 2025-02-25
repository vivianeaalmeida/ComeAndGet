import { Component, OnInit } from '@angular/core';
import {
  FormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
// import { AuthService } from '../../Services/auth.service';
import { Router, RouterLink } from '@angular/router';
// import { PageLoaderService } from '../../Services/page-loader.service';
import Swal from 'sweetalert2';
import { map } from 'rxjs';
import { AuthService } from '../../Services/auth.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  isVisible: boolean = false;
  isAuthorized: boolean = false;
  isLogged: any;
  isLoading: Boolean = false;

  constructor(
    private fb: FormBuilder,
    private loginServ: AuthService,
    private myRouter: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // campo obrigatório
      password: ['', [Validators.required, Validators.minLength(6)]], // campo obrigatório
    });
  }

  ngOnInit() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });

    this.loginServ.loggedSession.pipe(map((user) => user)).subscribe((user) => {
      this.isLogged = user;
    });
  }

  loginUser(): void {
    if (this.loginForm.invalid) {
      return;
    }
    this.isLoading = true;

    const { email, password } = this.loginForm.value;

    this.loginServ.login(email, password).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.isAuthorized = true;
        if (this.isLogged.roles == 'User') {
          this.myRouter.navigate(['/']);
        } else {
          this.myRouter.navigate(['dashboard']);
        }
      },
      error: (error: any) => {
        this.isLoading = false;
        this.isLoading = false;
        this.isAuthorized = false;
        Swal.fire({
          icon: 'error',
          title: 'Login failed! Please check your email and password.',
        });
        this.myRouter.navigate(['login']);
      },
    });
  }

  registerUser(): void {
    this.myRouter.navigate(['register']);
  }
}
