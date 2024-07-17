import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { StorageService } from './storage.service';


@Injectable()
export class AuthService {
  constructor(private http: HttpClient, private storageService: StorageService) {}

  login(username: string, password: string): Observable<any> {
    const credentials = { username, password };
    return this.http.post<any>(
      '/api/account/signin',
      credentials
    );
  }

  logout(): Observable<any> {
    localStorage.removeItem('authToken');
    return this.http.post<any>('/api/account/signout', {});
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }

  addReview(formData: FormData): Observable<any> {
    const authToken = sessionStorage.getItem('auth-user');
    if (!authToken) {
      throw new Error('No auth token available.');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${authToken}`,
    });

    return this.http.post<any>('/api/addReview', formData, {
      headers,
      withCredentials: true,
    });
  }

  updateUsername(userId: number, newUsername: string): Observable<any> {
    return this.http.put<any>(
      `/api/account/update-username/${userId}`,
      `${newUsername}`
    );
  }

  updatePassword(userId: number, newPassword: string): Observable<any> {
    return this.http.put<any>(
      `/api/account/update-password/${userId}`,
      `${newPassword}`
    );
  }


  getCurrentUser(): Observable<any> {
    return this.http.get<any>('/api/account/current-user', {
      withCredentials: true,
    });
  }

  register(
    username: string,
    email: string,
    password: string,
    name: string,
    country: string,
    gender: string,
    profilePicture: File
  ): Observable<any> {
    const formData = new FormData();
    formData.append('username', username);
    formData.append('email', email);
    formData.append('password', password);
    formData.append('name', name);
    formData.append('country', country);
    formData.append('gender', gender);
    formData.append('profilePicture', profilePicture);

    return this.http.post('/api/account/signup', formData);
  }

  isAuthenticated(): boolean {
    const user = this.storageService.getUser();
    return user && Object.keys(user).length > 0;
  }

  getAuthToken(): string | null {
    // Implement logic to get the auth token, e.g., from session storage
    return sessionStorage.getItem('authToken');
  }
}
