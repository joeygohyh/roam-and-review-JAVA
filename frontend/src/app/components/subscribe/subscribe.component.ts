import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrl: './subscribe.component.css',
})
export class SubscribeComponent {
  formData = {
    name: '',
    email: '',
  };

  responseMessage: string = '';

  constructor(private http: HttpClient) {}

  subscribe() {
    // Validate form data here if needed
    if (!this.formData.name || !this.formData.email) {
      this.responseMessage = 'Name and email are required.';
      return;
    }

    this.http.post<any>('/api/email/send', this.formData).subscribe(
      (response) => {
        console.log('Successfully subscribed:', response);
        this.responseMessage = 'Successfully subscribed!';
        alert('Successfully subscribed!');
        this.resetForm();
      },
      (error) => {
        console.error('Failed to subscribe:', error);
        this.responseMessage = 'Failed to subscribe.';
      }
    );
  }

  resetForm() {
    this.formData = {
      name: '',
      email: '',
    };
  }
}
