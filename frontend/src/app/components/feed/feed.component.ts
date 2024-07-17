import { Component, OnInit } from '@angular/core';
import { Review } from '../../models/models';
import { ReviewService } from '../../services/review.service';
import { first, Observable } from 'rxjs';
import { AuthService } from '../../_services/auth.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.css',
})
export class FeedComponent implements OnInit {
  reviews: Review[] = [];
  reviews$: Observable<Review[]> | undefined;
  currentUser$!: Observable<any>;
  username: string | null = null;

  constructor(
    private reviewService: ReviewService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.currentUser$ = this.authService.getCurrentUser();
    this.getList();
  }

  getList() {
    this.reviewService.getAllReviews().subscribe({
      next: (data) => {
        this.reviews = data.sort((a, b) => b.likes - a.likes); // Sort reviews by likes, most liked first
      },
      error: (err) => {
        console.error('Error fetching reviews', err);
      },
    });
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
