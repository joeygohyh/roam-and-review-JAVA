import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable, first } from 'rxjs';
import { AuthService } from '../../_services/auth.service';
import { Review } from '../../models/models';
import { ReviewService } from '../../services/review.service';
import { WebSocketService } from '../../services/web-socket.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css',
})
export class UserListComponent {
  reviews: Review[] = []; 
  reviews$: Observable<Review[]> | undefined; 

  username: string | null = null;
  profileUrl: string | null = null;
  currentUser$!: Observable<any>;

  constructor(
    private http: HttpClient,
    private reviewService: ReviewService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    this.currentUser$ = this.authService.getCurrentUser();
    this.route.params.subscribe((params) => {
      this.username = params['username'];
      this.profileUrl = params['profileUrl'];
      if (this.username) {
        this.getList();
      }
    });
  }

  showAlert(message: string): void {
    alert(`New Review: ${message}`);
  }

  

  deleteReview(reviewId: string): void {
    this.reviewService.deleteReview(reviewId).subscribe(
      () => {
        console.log('Review deleted successfully.');
        this.getList();
      },
      error => {
        console.error('Error deleting review:', error);
      }
    );
  }


  getList() {
    this.reviewService
      .getReviewsByUsername(this.username!)
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

    this.reviews$ = this.reviewService.getReviewsByUsername(this.username!);
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
