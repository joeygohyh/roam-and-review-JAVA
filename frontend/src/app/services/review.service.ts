import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Inject, Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Review } from '../models/models';
import { AuthService } from '../_services/auth.service';
import { StorageService } from '../_services/storage.service';

@Injectable()
export class ReviewService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  private readonly storageService = inject(StorageService);

  addReview(reviewData: FormData): Observable<any> {
    return this.http.post<any>(`/api/addReview`, reviewData, )
    .pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Unknown error occurred';
        if (error.error instanceof ErrorEvent) {
          errorMessage = `Error: ${error.error.message}`;
        } else {
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        console.error(errorMessage);
        return throwError(errorMessage);
      })
    );
}


  getAllReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(`/api/getAllReviews`).pipe(
      catchError((error) => {
        console.error('Error getting reviews:', error);
        return throwError(
          'Failed to retrieve reviews. Please try again later.'
        ); // Or handle the error appropriately
      })
    );
  }

  getReviewsByParkCode(parkCode: string): Observable<Review[]> {
    return this.http.get<Review[]>(`/api/getAllReviews/${parkCode}`).pipe(
      catchError((error) => {
        console.error(
          `Error getting reviews for park code ${parkCode}:`,
          error
        );
        return throwError(
          `Failed to retrieve reviews for park code ${parkCode}. Please try again later.`
        ); 
      })
    );
  }

  getReviewsByUsername(username: string): Observable<Review[]> {
    return this.http.get<Review[]>(`/api/getReviews/${username}`).pipe(
      catchError((error) => {
        console.error(
          `Error getting reviews for user: ${username}:`,
          error
        );
        return throwError(
          `Failed to retrieve reviews for user: ${username}. Please try again later.`
        ); 
      })
    );
  }

  deleteReview(reviewId: string): Observable<any> {
    return this.http.delete(`/api/deleteReview/${reviewId}`, { responseType: 'text' });
  }

  likeReview(reviewId: string, username: string): Observable<any> {
    return this.http
      .post(`/api/likeReview/${reviewId}`, { username })
      .pipe(
        catchError((error) => {
          console.error(`Error liking review ${reviewId}:`, error);
          return throwError(
            `Failed to like review ${reviewId}. Please try again later.`
          ); 
        })
      );
  }

  unlikeReview(reviewId: string, username: string): Observable<any> {
    return this.http
      .post(`/api/unlikeReview/${reviewId}`, { username })
      .pipe(
        catchError((error) => {
          console.error(`Error unliking review ${reviewId}:`, error);
          return throwError(
            `Failed to unlike review ${reviewId}. Please try again later.`
          ); 
        })
      );
  }


}
