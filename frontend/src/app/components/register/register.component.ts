import { Component } from '@angular/core';
import { AuthService } from '../../_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})

export class RegisterComponent {
  form: any = {
    username: '',
    email: '',
    password: '',
    name: '',
    country: '',
    gender: '',
    profilePicture: null,
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  profilePreview: string | ArrayBuffer | null =
    '//ssl.gstatic.com/accounts/ui/avatar_2x.png';

  genderOptions = [
    { label: 'Male', value: 'Male' },
    { label: 'Female', value: 'Female' },
    { label: 'Other', value: 'Other' },
  ];

  constructor(private authService: AuthService) {}

  onSubmit(): void {
    const { username, email, password, name, country, gender, profilePicture } =
      this.form;

    this.authService
      .register(
        username,
        email,
        password,
        name,
        country,
        gender,
        profilePicture
      )
      .subscribe({
        next: (data) => {
          console.log(data);
          this.isSuccessful = true;
          this.isSignUpFailed = false;
        },
        error: (err) => {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
        },
      });
  }

  onFileChange(event: Event): void {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.form.profilePicture = fileInput.files[0];

      const reader = new FileReader();
      reader.onload = (e) => {
        if (e.target?.result) {
          this.profilePreview = e.target.result;
        }
      };
      reader.readAsDataURL(this.form.profilePicture);
    }
  }
}
