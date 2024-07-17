import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, of } from 'rxjs';


const USER_KEY = 'auth-user';

@Injectable()
export class StorageService {
  constructor(private http: HttpClient) {}

  clean(): void {
    window.sessionStorage.clear();
  }

  saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : {};
  }

  isLoggedIn(): boolean {
    return !!window.sessionStorage.getItem(USER_KEY);
  }

  refreshUser(): Observable<any> {
    return this.http
      .get<any>('/api/account/current-user', {
        withCredentials: true,
      })
      .pipe(
        tap((user) => {
          this.saveUser(user);
        }),
        catchError((error) => {
          console.error('Error refreshing user:', error);
          return of(null); // Handle error gracefully
        })
      );
  }
}
