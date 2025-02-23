import { Component, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../Services/auth.service';
import { map } from 'rxjs';
import { FormsModule, NgModel } from '@angular/forms';
import { SearchService } from '../../Services/search.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, RouterLink, CommonModule, FormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  isDropdownOpen = false;
  isDropdown2Open = false;
  isDropdown3Open = false;
  isLoggedIn$!: any;
  checkRole: string | null = null;

  searchKeyword: string = '';

  constructor(private myrouter: Router, protected authService: AuthService, private searchService: SearchService) {}

  ngOnInit(): void {
    // Observa a autenticação do usuário
    this.isLoggedIn$ = this.authService.loggedSession.pipe(map((user) => !!user));

    // Obtém o role do usuário quando estiver logado
    this.authService.loggedSession.pipe(map((user) => user)).subscribe((user) => {
      if (user) {
        this.checkRole = user.roles; // Define o role do usuário
      }
    });
  }

  search() {
    this.searchService.search(this.searchKeyword);  // Emite a palavra-chave para o serviço
  }

  // Função para controlar a exibição do dropdown
  toggleDropdown(isOpen: boolean) {
    this.isDropdownOpen = isOpen;
  }

  toggleDropdown2(isOpen: boolean) {
    this.isDropdown2Open = isOpen;
  }

  toggleDropdown3(isOpen: boolean) {
    this.isDropdown3Open = isOpen;
  }

  toDash() {
    this.myrouter.navigate(['dashboard']);
  }
}
