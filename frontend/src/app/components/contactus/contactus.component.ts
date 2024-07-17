import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import emailjs, { EmailJSResponseStatus } from '@emailjs/browser';
import { environment } from '../../environment';

@Component({
  selector: 'app-contactus',
  templateUrl: './contactus.component.html',
  styleUrl: './contactus.component.css',
})
export class ContactusComponent implements OnInit {


  ngOnInit(): void {
    emailjs.init(environment.emailJsPublicKey);
  }

  sendEmail(contactForm: NgForm): void {
    emailjs
      .sendForm(
        environment.emailJsServiceId,
        environment.emailJsTemplateId,
        '#contact-form',
        environment.emailJsPublicKey
      )
      .then(
        (result: EmailJSResponseStatus) => {
          console.log('SUCCESS!', result.status, result.text);
          alert('Your feedback has been sent successfully!');
          contactForm.resetForm(); // Reset the form after successful submission
        },
        (error) => {
          console.log('FAILED...', error);
        }
      );
  }

}
