import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { SearchResult } from '../../models/models';
import { APIStore } from '../../stores/api.store';

@Component({
  selector: 'app-cached',
  templateUrl: './cached.component.html',
  styleUrl: './cached.component.css'
})
export class CachedComponent implements OnInit {
  q = '';
  result$!: Observable<SearchResult | undefined>;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private readonly apiStore: APIStore
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.q = params['q'];
      console.log('Search term:', this.q);
      
      // Fetch cached result if available
      this.result$ = this.apiStore.getSavedSearchesByQ(this.q);
    });
  }
}