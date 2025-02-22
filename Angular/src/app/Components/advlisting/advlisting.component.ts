import { Component, OnInit } from '@angular/core';
import { Category } from '../../Models/category';
import { User1 } from '../../Models/user1';
import { AuthService } from '../../Services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AdvService } from '../../Services/adv.service';
import { CategoryService } from '../../Services/category.service';
import { Adv } from '../../Models/adv';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { AdvertisementCardComponent } from '../advertisement-card/advertisement-card.component';
import { CommonModule, NgIf } from '@angular/common';
import { map } from 'rxjs';
import { ButtonCreateResAttemptComponent } from '../Buttons/button-create-res-attempt/button-create-res-attempt.component';

@Component({
  selector: 'app-advlisting',
  standalone: true,
  imports: [AdvertisementCardComponent, NgIf, CommonModule, ButtonCreateResAttemptComponent],
  templateUrl: './advlisting.component.html',
  styleUrl: './advlisting.component.css',
})
export class AdvlistingComponent implements OnInit {
  openedModal: boolean = false;
  selectedAdv: any | null = null;
  allCategories: Category[] | undefined;
  selectedCategory: string = 'all';
  advCollection: any[] = [];
  isLogged: any;
  user?: User1;
  existingAdv: number = 0;

  constructor(
    private loginServ: AuthService,
    private router: Router,
    private advServ: AdvService,
    private categServ: CategoryService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.loginServ.user.pipe(map((user) => user)).subscribe((user) => {
      this.isLogged = user;
    });

    this.getAwait();
    this.getAllCategories();
  }

  openModal(adv: Adv) {
    console.log('Selected Advertisement:', adv);
    this.selectedAdv = adv;
    this.openedModal = true;
  }

  closeModal() {
    this.openedModal = false;
    this.selectedAdv = null;
  }

  closeModalOut(event: Event) {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }

  getAllAvAdv(): Promise<any> {
    return new Promise((resolve) => {
      this.advServ.get('advertisements/active').subscribe(
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

  async getAwait() {
    await this.getAllAvAdv();
  }

  getAllCategories() {
    this.categServ.getCategories().subscribe((resp) => {
      this.allCategories = resp;
    });
  }

  filterByCategory(category: any) {
    const selectElement = category as HTMLSelectElement;
    this.selectedCategory = selectElement.value;
    this.existingAdv = 0;
    for (let i = 0; i < this.advCollection.length; i++) {
      if (
        this.advCollection[i].item.category.designation == this.selectedCategory
      ) {
        this.existingAdv++;
      }
    }
  }

  getSafeImageUrl(imagePath: string): SafeResourceUrl {
    //console.log(imagePath);
    return this.sanitizer.bypassSecurityTrustResourceUrl(
      'http://localhost:8080/' + imagePath
    );
  }

  redirect() {
    if (this.isLogged != null && this.isLogged.roles === 'User') {
      console.log('Estou logado!');
    } else {
      this.router.navigate(['login']);
    }
  }
}
