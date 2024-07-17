import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {}

  getPublicContent(): Observable<any> {
    return this.http.get('/api/account/all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get('/api/account/user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get('api/account/mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get('api/account/admin', { responseType: 'text' });
  }
}
