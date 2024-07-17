import { Component, Input, OnInit } from '@angular/core';
import { Campground } from '../../../models/models';
import { APIService } from '../../../services/api.service';

@Component({
  selector: 'app-campgrounds',
  templateUrl: './campgrounds.component.html',
  styleUrl: './campgrounds.component.css'
})
export class CampgroundsComponent implements OnInit{

  @Input() parkCode!: string;
  campgrounds: Campground[] = [];
  responsiveOptions: any[] = [];


  constructor(private apiService: APIService) { }

  ngOnInit(): void {
    this.responsiveOptions = [
      {
        breakpoint: '1300px',
        numVisible: 3,
        numScroll: 1
      },
      {
        breakpoint: '1020px',
        numVisible: 2,
        numScroll: 1
      },
      {
        breakpoint: '720px',
        numVisible: 1,
        numScroll: 1
      }
    ];

    this.getCampgrounds();
  }


  getCampgrounds(): void {
    if (this.parkCode) {
      this.apiService.getCampgrounds(this.parkCode).subscribe(
        (campgrounds) => this.campgrounds = campgrounds,
        (error) => console.error('Error fetching activities', error)
      );
    }
  }

}
