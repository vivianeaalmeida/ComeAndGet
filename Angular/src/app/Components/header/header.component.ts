import { Component, HostListener, OnInit } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../Services/auth.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, RouterLink, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  isDropdownOpen = false;
  isLoggedIn$!: any;
  checkRole: string | null = null;

  constructor(private myrouter: Router, protected authService: AuthService) {}

  ngOnInit(): void {
    // Observa a autenticação do usuário
    this.isLoggedIn$ = this.authService.user.pipe(map((user) => !!user));

    // Obtém o role do usuário quando estiver logado
    this.authService.user.pipe(map((user) => user)).subscribe((user) => {
      if (user) {
        this.checkRole = user.roles; // Define o role do usuário
      }
    });
  }

  // Função para controlar a exibição do dropdown
  toggleDropdown(isOpen: boolean) {
    this.isDropdownOpen = isOpen;
  }
}
