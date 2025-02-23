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
import { MapComponent } from '../map/map.component';
import { AdvertisementModalComponent } from '../advertisement-modal/advertisement-modal.component';
import { MunicipalityService } from '../../Services/municipality.service';
import { SearchService } from '../../Services/search.service';

@Component({
  selector: 'app-advlisting',
  standalone: true,
  imports: [AdvertisementCardComponent, CommonModule, AdvertisementModalComponent],
  templateUrl: './advlisting.component.html',
  styleUrl: './advlisting.component.css',
})
export class AdvlistingComponent implements OnInit {
  openedModal: boolean = false;
  selectedAdv: any | null = null;
  allCategories: Category[] | undefined;
  allMunicipalities: string[] = [];
  selectedCategory: string = 'all';
  selectedMunicipality: string = 'all'
  searchKeyword: string = '';
  advCollection: any[] = [];
  isLogged: any;
  user?: User1;
  existingAdv: number = 0;
  hasAdvs: boolean = true;

  constructor(
    private loginServ: AuthService,
    private router: Router,
    private advServ: AdvService,
    private categServ: CategoryService,
    private municipalitiesServ: MunicipalityService,
    private sanitizer: DomSanitizer,
    private searchService: SearchService
  ) { }

  ngOnInit(): void {
    this.loginServ.user.pipe(map((user) => user)).subscribe((user) => {
      this.isLogged = user;
    });

    this.getAwait();
    this.getAllCategories();
    this.getAllMunicipalities();

    this.searchService.search$.subscribe((keyword: string) => {
      this.searchKeyword = keyword;  // updates the keyword
      this.filterAdvertisements();  // Chama a função para filtrar os anúncios
    });
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
    this.categServ.getCategories().subscribe({
      next: (resp) => {
        this.allCategories = resp;
      },
      error: (err) => {
        console.error('Error feching categories:', err);
      },
    });
  }

  getAllMunicipalities() {
    this.municipalitiesServ.getMunicipalities().subscribe({
      next: (resp) => {
        this.allMunicipalities = resp;
      },
      error: (err) => {
        console.error('Error feching municipalities:', err);
      },
    });
  }

  filterAdvertisements() {
    // Aplica o filtro de pesquisa por keyword, municipio e categoria
    this.advServ.searchAdvertisement(
      this.searchKeyword || undefined, // Filtra pelo termo de procura se existir
      this.selectedMunicipality === 'all' ? undefined : this.selectedMunicipality, // Filtra por município se selecionado
      this.selectedCategory === 'all' ? undefined : this.selectedCategory // Filtra por categoria se selecionada
    ).subscribe((filteredAds) => {
      this.advCollection = filteredAds;
      this.hasAdvs = this.advCollection.length > 0; 
      this.existingAdv = this.advCollection.length;
    });
  }
  

  filterByCategory(category: any) {
    const selectElement = category as HTMLSelectElement;
    this.selectedCategory = selectElement.value;
    //console.log('Selected Category:', this.selectedCategory);

    this.filterAdvertisements();
  }

  filterByMunicipality(municipality: any) {
    const selectElement = municipality as HTMLSelectElement;
    this.selectedMunicipality = selectElement.value;
    //console.log('Selected Municipality:', this.selectedMunicipality);

    this.filterAdvertisements();
  }

  getSafeImageUrl(imagePath: string): SafeResourceUrl {
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