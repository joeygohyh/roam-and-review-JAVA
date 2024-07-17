import { Component, Input, OnInit } from '@angular/core';
import { Activity } from '../../../models/models';
import { APIService } from '../../../services/api.service';

@Component({
  selector: 'app-activities',
  templateUrl: './activities.component.html',
  styleUrl: './activities.component.css'
})


export class ActivitiesComponent implements OnInit {
  @Input() parkCode!: string;
  activities: Activity[] = [];
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

    this.getActivities();
  }

  getActivities(): void {
    if (this.parkCode) {
      this.apiService.getActivities(this.parkCode).subscribe(
        (activities) => this.activities = activities,
        (error) => console.error('Error fetching activities', error)
      );
    }
  }
}