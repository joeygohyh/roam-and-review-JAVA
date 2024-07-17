import { Component, Input, OnInit } from '@angular/core';
import { VisitorCentre } from '../../../models/models';
import { APIService } from '../../../services/api.service';

@Component({
  selector: 'app-visitorcentres',
  templateUrl: './visitorcentres.component.html',
  styleUrl: './visitorcentres.component.css'
})
export class VisitorcentresComponent implements OnInit {

  @Input() parkCode!: string;
  visitorCentres: VisitorCentre[] = [];
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

    this.getVisitorCentres();
  }


  getVisitorCentres(): void {
    if (this.parkCode) {
      this.apiService.getVisitorCentres(this.parkCode).subscribe(
        (visitorCentres) => this.visitorCentres = visitorCentres,
        (error) => console.error('Error fetching activities', error)
      );
    }
  }
}