import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { loadStripe } from '@stripe/stripe-js';
import { environment } from '../../../environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-coffee',
  templateUrl: './coffee.component.html',
  styleUrl: './coffee.component.css',
})
export class CoffeeComponent {
  // We load  Stripe
  stripePromise = loadStripe(environment.stripe_publishable_key);
  // constructor(private http: HttpClient) {}
  constructor(private http: HttpClient, private router: Router) {}

  async pay(): Promise<void> {
    const payment = {
      name: 'coffee',
      currency: 'sgd',
      amount: 500,
      quantity: '1',
      // cancelUrl: 'http://localhost:4200/cancel',
      // successUrl: 'http://localhost:4200/success',
      cancelUrl: `${window.location.origin}/#/cancel`,
      successUrl: `${window.location.origin}/#/success`,
    };

    const stripe = await this.stripePromise;

    if (stripe) {
      this.http
        .post(`/api/coffee`, payment)
        // .post(`${environment.serverUrl}/payment`, payment)
        .subscribe((data: any) => {
          stripe.redirectToCheckout({
            sessionId: data.id,
          });
        });
    } else {
      console.error('Stripe has not been loaded.');
      // Handle error or retry loading Stripe
    }
  }
}
