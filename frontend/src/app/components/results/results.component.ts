import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Park, SearchResult } from '../../models/models';
import { APIStore } from '../../stores/api.store';
import { APIService } from '../../services/api.service';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrl: './results.component.css',
})



export class ResultsComponent implements OnInit {
  q!: string;
  parks!: Park[];

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly apiService: APIService,
    private readonly apiStore: APIStore
  ) {}

  ngOnInit(): void {
    this.q = this.activatedRoute.snapshot.queryParams['q'];
    console.log('Searching for: ' + this.q);

    // Attempt to fetch from APIStore first, fallback to APIService if not cached
    this.apiStore.getSavedSearchesByQ(this.q).subscribe(
      cachedResult => {
        if (cachedResult) {
          this.parks = cachedResult.parks;
        } else {
          this.apiService.search(this.q).subscribe(result => {
            this.parks = result.parks;
          });
        }
      },
      error => {
        console.error('Error fetching cached result:', error);
        // Fallback to API service on error
        this.apiService.search(this.q).subscribe(result => {
          this.parks = result.parks;
        });
      }
    );
  }


}