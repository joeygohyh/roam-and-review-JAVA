import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FullPark } from '../../models/models';
import { APIService } from '../../services/api.service';

@Component({
  selector: 'app-park',
  templateUrl: './park.component.html',
  styleUrl: './park.component.css',
})
export class ParkComponent implements OnInit {
  parkCode: string = '';
  park!: FullPark;
  galleriaImages: any[] = [];
  responsiveOptions: any[] = [];

  mapOptions: google.maps.MapOptions = {
    mapId: 'DEMO_MAP_ID',
    center: { lat: -31, lng: 147 },
    zoom: 10,
  };

  locations: any[] = [];

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly apiService: APIService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      this.parkCode = params.get('parkCode') || ''; 
      console.log('Park Code:', this.parkCode);
      this.apiService.getPark(this.parkCode).subscribe((park) => {
        this.park = park;
        const lat = parseFloat(this.park.latitude);
        const lng = parseFloat(this.park.longitude);

        this.mapOptions.center = { lat, lng };
        this.galleriaImages = this.park.images.map((image) => ({
          itemImageSrc: image.url,
          thumbnailImageSrc: image.url, // Use the same URL for both item and thumbnail
          alt: image.altText,
          title: image.caption,
        }));

        this.locations = [{ lat, lng }]; 
      });
    });
  }
}
