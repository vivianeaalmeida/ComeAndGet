import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './Components/header/header.component';
import { ButtonComponent } from './Components/Buttons/button/button.component';
import { AdvertisementCardComponent } from './Components/advertisement-card/advertisement-card.component';
import { FooterComponent } from './Components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderComponent,
    ButtonComponent,
    AdvertisementCardComponent,
    FooterComponent,
    CommonModule,
    NgxDatatableModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'Angular';
}
