import { Component, OnInit } from '@angular/core';
import { StorageService } from '../../_services/storage.service';
import { AuthService } from '../../_services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent implements OnInit {


  currentUser: any;
  isEditingUsername: boolean = false;
  isEditingPassword: boolean = false;
  newUsername: string = '';
  newPassword: string = '';
  errorMessageUsername = '';
  errorMessagePassword = '';

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private router: Router
  ) {}

 

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
    
    if (!this.currentUser || Object.keys(this.currentUser).length === 0) {
      this.router.navigate(['/login']);
    }
  }

  showUpdateForm: boolean = false;


  toggleEditUsername(): void {
    this.isEditingUsername = !this.isEditingUsername;
    if (!this.isEditingUsername) {
      this.newUsername = ''; 
    }
  }

  toggleEditPassword(): void {
    this.isEditingPassword = !this.isEditingPassword;
    if (!this.isEditingPassword) {
      this.newPassword = ''; 
    }
  }


  onUpdateUsername(newUsername: string): void {
    const userId = this.currentUser.id; 
    this.authService.updateUsername(userId, newUsername).subscribe({
      next: (response) => {
        console.log('Username updated successfully:', response);
        this.refreshUser(); 
       
        this.isEditingUsername = false;
        this.errorMessageUsername = ''; 

      },
      error: (error) => {
        console.error('Error updating username:', error);
        this.errorMessageUsername = 'Username already exists.'; 

       
      },
    });
  }

  onUpdatePassword(newPassword: string): void {
    const userId = this.currentUser.id; 
    this.authService.updatePassword(userId, newPassword).subscribe({
      next: (response) => {
        console.log('Username updated successfully:', response);
        this.refreshUser(); 
       
        this.isEditingPassword = false;
        this.errorMessagePassword = '';

      },
      error: (error) => {
        console.error('Error updating username:', error);

      },
    });
  }

  refreshUser(): void {
    this.currentUser = this.storageService.getUser();
  }
}
