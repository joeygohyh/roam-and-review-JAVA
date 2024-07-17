import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, first } from 'rxjs';
import { AuthService } from '../../../_services/auth.service';
import { Review } from '../../../models/models';
import { ReviewService } from '../../../services/review.service';
import { WebSocketService } from '../../../services/web-socket.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrl: './list.component.css',
})
export class ListComponent implements OnInit {
  reviews: Review[] = []; // Initialize as empty array
  reviews$: Observable<Review[]> | undefined; // Initialize as undefined

  parkCode: string | null = null;
  currentUser$!: Observable<any>;

  constructor(
    private http: HttpClient,
    private reviewService: ReviewService,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUser$ = this.authService.getCurrentUser();
    this.route.params.subscribe((params) => {
      this.parkCode = params['parkCode'];
      if (this.parkCode) {
        this.getList();
      }
    });
  }

  showAlert(message: string): void {
    alert(`New Review: ${message}`);
    // You can use other notification libraries or custom components here
  }

  getList() {
    this.reviewService
      .getReviewsByParkCode(this.parkCode!)
      .pipe(first()) // Take the first emitted value and complete
      .subscribe({
        next: (response: Review[]) => {
          this.reviews = response;
        },
        error: (error) => {
          console.error('Error fetching reviews:', error);
          alert('Failed to fetch reviews. Please try again later.');
        },
      });

    this.reviews$ = this.reviewService.getReviewsByParkCode(this.parkCode!);
  }

  likeReview(reviewId: string) {
    this.currentUser$.subscribe((user) => {
      const username = user?.username;
      if (username) {
        this.reviewService.likeReview(reviewId, username).subscribe(
          (response) => {
            console.log('Review liked successfully');
            // Refresh reviews after liking
            this.getList();
          },
          (error) => {
            console.error('Error liking review:', error);
            alert('You have already liked this review.');
          }
        );
      } else {
        alert('You must be logged in to like a review.');
      }
    });
  }

  unlikeReview(reviewId: string) {
    this.currentUser$.subscribe((user) => {
      const username = user?.username;
      if (username) {
        this.reviewService.unlikeReview(reviewId, username).subscribe(
          (response) => {
            console.log('Review unliked successfully');
            // Refresh reviews after unliking
            this.getList();
          },
          (error) => {
            console.error('Error unliking review:', error);
            alert('Failed to unlike review. Please try again later.');
          }
        );
      } else {
        alert('You must be logged in to unlike a review.');
      }
    });
  }
}
