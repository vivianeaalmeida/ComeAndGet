import { Component, OnInit, Pipe } from '@angular/core';
import { AuthService } from '../../Services/auth.service';
import { User1 } from '../../Models/user1';
import { CommonModule, NgIf } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import { AdvService } from '../../Services/adv.service';
import { map } from 'rxjs';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ReservationAttemptListComponent } from '../reservation-attempt-list/reservation-attempt-list.component';
import { ButtonUpdateAdvComponent } from '../Buttons/button-update-adv/button-update-adv.component';
import { ButtonDeactivateAdvertisementComponent } from '../Buttons/button-deactivate-advertisement/button-deactivate-advertisement.component';
import { Adv } from '../../Models/adv';
import { Advertisement } from '../../Models/advertisement';
import { AdvertisementModalComponent } from '../advertisement-modal/advertisement-modal.component';
import { TipService } from '../../Services/tip.service';
import { Tip } from '../../Models/tip';
import { FavoriteTipDetailComponent } from '../favorite-tip-detail/favorite-tip-detail.component';

@Component({
  selector: 'app-user-area',
  standalone: true,
  imports: [NgIf,
    ButtonComponent, CommonModule, ButtonUpdateAdvComponent,
    ReservationAttemptListComponent, ButtonDeactivateAdvertisementComponent,
    AdvertisementModalComponent,
    FavoriteTipDetailComponent
  ],
  templateUrl: './user-area.component.html',
  styleUrl: './user-area.component.css',
})
export class UserAreaComponent implements OnInit {
  advCollection: Advertisement[] = [];
  tipCollection: Tip[] = [];

  menus: any = {
    profile: true,
    myAdvs: false,
    myRequests: false,
  };

  user?: User1;
  name?: string;
  userId?: string;
  isLogged: any;
  selectedAdv: any | null = null;
  openedModal: boolean = false;

  constructor(
    private authService: AuthService,
    private advServ: AdvService,
    private tipService: TipService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    console.log(this.menus);
    this.profileInfo();
    this.authService.loggedSession.pipe(map((user) => user)).subscribe((user) => {
      this.isLogged = user;
    });
  }

  changeView(menu: string) {
    this.menus['profile'] = menu == 'profile' ? true : false;
    this.menus['myAdvs'] = menu == 'myAdvs' ? true : false;
    this.menus['myRequests'] = menu == 'myRequests' ? true : false;
    this.menus['myFavoriteTips'] = menu == 'myFavoriteTips' ? true : false;

    if (menu == 'myAdvs') {
      this.asyncGetMyAdvs();
    } else if (menu == 'myRequests') {
    } else if (menu == 'myFavoriteTips') {
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
      this.advServ.get('advertisements/users/' + this.userId).subscribe(
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
      this.advServ.get('/advertisements/users/' + this.userId).subscribe(
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


  getMyFavoriteTips(): Promise<any> {
    return new Promise((resolve) => {
      this.tipService.getFavoriteTipsByUserId(this.userId!).subscribe(
        (resp) => {
          this.tipCollection = resp;
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

  selectAdv(adv: any) {
    this.selectedAdv = adv;
    console.log('Selected adv:' + this.selectAdv);
  }

  openModal(adv: Advertisement) {
    console.log('Selected Advertisement:', adv);
    this.selectedAdv = adv;
    this.openedModal = true;
  }

  closeModal() {
    this.openedModal = false;
    this.selectedAdv = null;
  }
}
