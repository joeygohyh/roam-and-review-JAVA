import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { AuthService } from '../../../_services/auth.service';
import { ReviewService } from '../../../services/review.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { StorageService } from '../../../_services/storage.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css'], 
})
export class ReviewComponent implements OnInit {
  form: FormGroup;
  previewUrl: string | ArrayBuffer | null = null;
  parkCode: string | null = null;
  usernamePlaceholder: string = '-';
  countryPlaceholder: string = '-';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private authService: AuthService,
    private reviewService: ReviewService,
    private storageService: StorageService
  ) {
    this.form = this.formBuilder.group({
      file: [null, Validators.required],
      review: ['', Validators.required],
      location: ['', Validators.required],
      parkCode: [this.parkCode, Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.parkCode = params['parkCode'];
      this.form.patchValue({
        parkCode: this.parkCode,
      });
    });

    this.populateUserDetails();
  }

  populateUserDetails(): void {
    const user = this.storageService.getUser();
    if (user) {
      this.usernamePlaceholder = user.username || '-';
      this.countryPlaceholder = user.country || '-';
    }
  }

  submit(): void {
    if (!this.storageService.isLoggedIn()) {
      this.confirmationService.confirm({
        header: 'Login Required',
        message: 'Please sign in to submit a review.',
        accept: () => {
          this.router.navigate(['/login']);
        },
      });
      return;
    }

    const formData = new FormData();
    formData.append('review', this.form.get('review')?.value);
    formData.append('location', this.form.get('location')?.value);
    formData.append('parkCode', this.form.get('parkCode')?.value);
    formData.append('file', this.form.get('file')?.value);

    this.authService.addReview(formData).subscribe(
      (response: any) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Review submitted successfully!',
          life: 3000,
        });
        this.resetForm();
      },
      (error: any) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to submit review. Please try again later.',
          life: 3000,
        });
      }
    );
  }

  onFileChange(event: Event): void {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.form.get('file')?.setValue(fileInput.files[0]);

      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result;
      };
      reader.readAsDataURL(fileInput.files[0]);
    }
  }

  resetForm(): void {
    this.form.reset();
    this.form.patchValue({
      parkCode: this.parkCode,
    });
    this.previewUrl = null;
  }
}
