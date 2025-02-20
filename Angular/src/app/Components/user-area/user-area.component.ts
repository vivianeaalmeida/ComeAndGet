import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../Services/auth.service';
import { User1 } from '../../Models/user1';
import { NgIf } from '@angular/common';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-user-area',
  standalone: true,
  imports: [NgIf, ButtonComponent],
  templateUrl: './user-area.component.html',
  styleUrl: './user-area.component.css',
})
export class UserAreaComponent implements OnInit {
  constructor(private authService: AuthService) {}

  user: User1 | null = null;

  ngOnInit(): void {
    console.log('Initial auth state:', this.authService.user);
    this.authService.getUser().subscribe((user) => {
      console.log('User data update:', user);
      this.user = user;
    });
  }
}
