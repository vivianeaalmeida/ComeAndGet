import { Component, HostListener, OnInit } from '@angular/core';
import { ButtonComponent } from '../button/button.component';
import { NgIf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from '../../Services/auth.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  isDropdownOpen = false;
  isLoggedIn$!: any;
  searchIsbn: string = '';
  checkRole: any;

  constructor(private myrouter: Router, protected authService: AuthService) {}

  ngOnInit(): void {
    this.isLoggedIn$ = this.authService.user.pipe(map((user) => !!user));
    this.authService.user.pipe(map((user) => user)).subscribe((user) => {
      this.checkRole = user;
      this.checkRole = this.checkRole.role;
    });
  }

  // Função para controlar a exibição do dropdown
  toggleDropdown(isOpen: boolean) {
    this.isDropdownOpen = isOpen;
  }
}
