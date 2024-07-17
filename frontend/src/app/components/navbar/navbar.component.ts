import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { AuthService } from '../../_services/auth.service';
import { StorageService } from '../../_services/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;
  items: MenuItem[] = [];

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }

    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: 'home',
      },
      {
        label: 'Search',
        icon: 'pi pi-search',
        routerLink: 'search',
      },
  
      {
        label: 'Admin Board',
        icon: 'pi pi-user',
        routerLink: 'admin',
        visible: this.showAdminBoard,
      },
      {
        label: 'Moderator Board',
        icon: 'pi pi-users',
        routerLink: 'mod',
        visible: this.showModeratorBoard,
      },
      // {
      //   label: 'User',
      //   icon: 'pi pi-user',
      //   routerLink: 'user',
      //   visible: this.isLoggedIn,
      // },
      {
        label: 'Sign Up',
        icon: 'pi pi-user-plus',
        routerLink: 'register',
        visible: !this.isLoggedIn,
      },
      {
        label: 'Login',
        icon: 'pi pi-sign-in',
        routerLink: 'login',
        visible: !this.isLoggedIn,
      },
      {
        label: 'Feed',
        icon: 'pi pi-globe',
        routerLink: 'feed',
      },
      {
        label: 'Meet',
        icon: 'pi pi-share-alt',
        routerLink: 'meet',
      },
      {
        label: this.username,
        icon: 'pi pi-user',
        routerLink: 'profile',
        visible: this.isLoggedIn,
        items: [
          {
            label: 'Logout',
            icon: 'pi pi-sign-out',
            command: () => this.logout(),
          },
          {
            label: 'My Reviews',
            icon: 'pi pi-comment',
            routerLink: ['profile', this.username],

          },
        ],
      },
      {
        label: 'Newsletter',
        icon: 'pi pi-envelope',
        routerLink: 'subscribe',
      },
      {
        label: 'Contact Us',
        icon: 'pi pi-link',
        routerLink: 'contactus',
      },
    ];
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: (res) => {
        console.log(res);
        this.storageService.clean();
        this.router.navigate(['/login']).then(() => {
          window.location.reload();
        });


      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
