// api service to call backend

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, firstValueFrom, lastValueFrom, map, tap } from 'rxjs';
import {
  Activity,
  Campground,
  FullPark,
  Park,
  SearchResult,
  VisitorCentre,
} from '../models/models';
import { APIStore } from '../stores/api.store';

@Injectable()
export class APIService {
  private readonly http = inject(HttpClient);
  private readonly apiStore = inject(APIStore);
  private searchUrl = '/api/search';


  // observable
  search(q: string): Observable<SearchResult> {
    const params = new HttpParams().set('q', q);
    return this.http.get<Park[]>(this.searchUrl, { params }).pipe(
      map((parks) => ({
        date: Date.now(),
        q,
        parks,
      })),
      tap((result) => this.apiStore.saveResult(result))
    );
  }

  private searchAllUrl = '/api/search';

  // observable
  searchAll(q: string): Observable<SearchResult> {
    const params = new HttpParams().set('q', q);
    return this.http.get<Park[]>(this.searchAllUrl, { params }).pipe(
      map((parks) => ({
        date: Date.now(),
        q,
        parks,
      })),
      // tap((result) => this.apiStore.saveResult(result))
    );
  }

  private getParkUrl = '/api/park';

  getPark(parkCode: string): Observable<FullPark> {
    return this.http.get<FullPark>(`${this.getParkUrl}/${parkCode}`);
  }

  private getVisitorCentresUrl = '/api/visitor_centres'; 

  getVisitorCentres(parkCode: string): Observable<VisitorCentre[]> {
    return this.http.get<VisitorCentre[]>(
      `${this.getVisitorCentresUrl}?parkCode=${parkCode}`
    );
  }

  private getActivitiesUrl = '/api/activities'; 

  getActivities(parkCode: string): Observable<Activity[]> {
    return this.http.get<Activity[]>(
      `${this.getActivitiesUrl}?parkCode=${parkCode}`
    );
  }

  private getCampgroundsUrl = '/api/campgrounds'; 

  getCampgrounds(parkCode: string): Observable<Campground[]> {
    return this.http.get<Campground[]>(
      `${this.getCampgroundsUrl}?parkCode=${parkCode}`
    );
  }
}

