import { Component, OnInit, Pipe } from '@angular/core';
import { AuthService } from '../../Services/auth.service';
import { User1 } from '../../Models/user1';
import { CommonModule, NgIf } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import { AdvService } from '../../Services/adv.service';
import { map } from 'rxjs';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ReservationAttemptListComponent } from '../reservation-attempt-list/reservation-attempt-list.component';

@Component({
  selector: 'app-user-area',
  standalone: true,
  imports: [NgIf, ButtonComponent, CommonModule, ReservationAttemptListComponent],
  templateUrl: './user-area.component.html',
  styleUrl: './user-area.component.css',
})
export class UserAreaComponent implements OnInit {
  advCollection: any[] = [];

  menus: any = {
    profile: true,
    myAdvs: false,
    myRequests: false,
  };

  user?: User1;
  name?: string;
  userId?: string;
  isLogged: any;

  constructor(
    private authService: AuthService,
    private advServ: AdvService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    console.log(this.menus);
    this.profileInfo();
    this.authService.user.pipe(map((user) => user)).subscribe((user) => {
      this.isLogged = user;
    });
  }

  changeView(menu: string) {
    this.menus['profile'] = menu == 'profile' ? true : false;
    this.menus['myAdvs'] = menu == 'myAdvs' ? true : false;
    this.menus['myRequests'] = menu == 'myRequests' ? true : false;

    if (menu == 'myAdvs') {
      this.asyncGetMyAdvs();
    } else if (menu == 'myRequests') {
    }
  }

  profileInfo() {
    this.authService.getUser().subscribe((user) => {
      console.log('User data update:', user);
      this.user = user;
      this.userId = user.userId;
      let arr = this.user.username.split(' ');
      this.name = arr[0];
    });
  }

  async asyncGetMyAdvs() {
    await this.getMyAdvs();
  }

  getMyAdvs(): Promise<any> {
    return new Promise((resolve) => {
      this.advServ.get('users/' + this.userId + '/advertisements').subscribe(
        (resp) => {
          this.advCollection = resp;
          resolve(true);
        },
        (error) => {
          console.error(error);
        }
      );
    });
  }

  async asyncGetMyRequests() {
    await this.getMyAdvs();
  }

  getMyRequests(): Promise<any> {
    return new Promise((resolve) => {
      this.advServ.get('users/' + this.userId + '/advertisements').subscribe(
        (resp) => {
          this.advCollection = resp;
          resolve(true);
        },
        (error) => {
          console.error(error);
        }
      );
    });
  }

  getSafeImageUrl(imagePath: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(
      'http://localhost:8080/' + imagePath
    );
  }
}
