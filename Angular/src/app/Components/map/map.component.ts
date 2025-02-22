import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { GoogleMap } from '@angular/google-maps';
import * as L from 'leaflet';


@Component({
  selector: 'app-map',
  standalone: true,
  imports: [],
  templateUrl: './map.component.html',
  styleUrl: './map.component.css'
})
export class MapComponent implements OnChanges {
  @Input() municipality!: string;
  map!: L.Map;
  marker!: L.Marker;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['municipality'] && this.municipality) {
      this.getCoordinates(this.municipality);
    }
  }

  getCoordinates(location: string) {
    if (!location) {
      console.error("Localização inválida");
      return;
    }

    const formattedLocation = `${location}, Portugal`;
    const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(formattedLocation)}`;

    fetch(url)
      .then(response => response.json())
      .then(data => {
        if (data.length > 0) {
          const lat = parseFloat(data[0].lat);
          const lon = parseFloat(data[0].lon);
          this.setMap(lat, lon);
        } else {
          console.error("Error fetching location to:", location);
        }
      })
      .catch(error => console.error("Error searching for coordinates:", error));
  }

  setMap(lat: number, lon: number) {
    if (!this.map) {
      // Creates the map and defines its zoom to 14
      this.map = L.map('map').setView([lat, lon], 14);

      // Defines the map layer
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      }).addTo(this.map);

      const customIcon = L.icon({
        iconUrl: '../../../assets/map-marker.png',
        iconSize: [40, 40],
        iconAnchor: [20, 40],
        popupAnchor: [0, -40]
      });

      this.marker = L.marker([lat, lon], { icon: customIcon }).addTo(this.map)
        .openPopup();
    } else {
      this.map.setView([lat, lon], 14); // Defines the zoom map if the map is alerady created
      this.marker.setLatLng([lat, lon]);
    }
  }
}