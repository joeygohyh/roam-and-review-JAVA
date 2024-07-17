import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './_services/auth.service';
import { StorageService } from './_services/storage.service';
import { UserService } from './_services/user.service';
import { NavbarComponent } from './components/navbar/navbar.component';
import { PanelModule } from 'primeng/panel';
import { GoogleMapsModule } from '@angular/google-maps';
import { GoogleMap } from '@angular/google-maps';

///
import { MenubarModule } from 'primeng/menubar';
import { AvatarModule } from 'primeng/avatar';
import { BadgeModule } from 'primeng/badge';
import { PasswordModule } from 'primeng/password';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { DataViewModule } from 'primeng/dataview';
import { FloatLabelModule } from 'primeng/floatlabel';
import { TagModule } from 'primeng/tag';
import { RatingModule } from 'primeng/rating';
import { GalleriaModule } from 'primeng/galleria';
import { AccordionModule } from 'primeng/accordion';
import { FieldsetModule } from 'primeng/fieldset';
import { CarouselModule } from 'primeng/carousel';
import { ChipModule } from 'primeng/chip';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { MessagesModule } from 'primeng/messages';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';



//
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProfileComponent } from './components/profile/profile.component';
import { SearchComponent } from './components/search/search.component';
import { APIStore } from './stores/api.store';
import { ResultsComponent } from './components/results/results.component';
import { APIService } from './services/api.service';
import { ParkComponent } from './components/park/park.component';
import { ActivitiesComponent } from './components/park/activities/activities.component';
import { VisitorcentresComponent } from './components/park/visitorcentres/visitorcentres.component';
import { CampgroundsComponent } from './components/park/campgrounds/campgrounds.component';
import { ListComponent } from './components/park/list/list.component';
import { ReviewService } from './services/review.service';
import { ReviewComponent } from './components/park/review/review.component';
import { ContactusComponent } from './components/contactus/contactus.component';
import { SubscribeComponent } from './components/subscribe/subscribe.component';
import { CoffeeComponent } from './components/contactus/coffee/coffee.component';
import { SuccessComponent } from './components/contactus/success/success.component';
import { CancelComponent } from './components/contactus/cancel/cancel.component';
import { httpInterceptorProviders } from './_helpers/http.interceptor';
import { authGuard } from './components/guards';
import { FeedComponent } from './components/feed/feed.component';
import { WebSocketService } from './services/web-socket.service';
import { MapComponent } from './components/map/map.component';
import { MeetComponent } from './components/meet/meet.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { CachedComponent } from './components/cached/cached.component';
import { ServiceWorkerModule } from '@angular/service-worker';

//

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
  { path: 'contactus', component: ContactusComponent },
  { path: 'subscribe', component: SubscribeComponent },
  {
    path: 'coffee',
    component: CoffeeComponent,
  },
  { path: 'cancel', component: CancelComponent },
  { path: 'success', component: SuccessComponent },
  // { path: 'user', component: BoardUserComponent },
  // { path: 'mod', component: BoardModeratorComponent },
  // { path: 'admin', component: BoardAdminComponent },
  { path: 'park/:parkCode', component: ParkComponent },
  { path: 'search', component: SearchComponent },
  { path: 'cached/:q', component: CachedComponent },
  { path: 'results', component: ResultsComponent },
  { path: 'feed', component: FeedComponent },
  { path: 'meet', component: MeetComponent },
  { path: 'profile/:username', component: UserListComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
    ProfileComponent,
    SearchComponent,
    ResultsComponent,
    ParkComponent,
    ActivitiesComponent,
    VisitorcentresComponent,
    CampgroundsComponent,
    ListComponent,
    ReviewComponent,
    ContactusComponent,
    SubscribeComponent,
    CoffeeComponent,
    SuccessComponent,
    CancelComponent,
    FeedComponent,
    MapComponent,
    MeetComponent,
    UserListComponent,
    CachedComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes, { useHash: true }),
    MenubarModule,
    AvatarModule,
    BadgeModule,
    PasswordModule,
    DropdownModule,
    ButtonModule,
    FileUploadModule,
    InputTextModule,
    CardModule,
    FloatLabelModule,
    BrowserAnimationsModule,
    DataViewModule,
    TagModule,
    RatingModule,
    GalleriaModule,
    AccordionModule,
    FieldsetModule,
    CarouselModule,
    PanelModule,
    ChipModule,
    ConfirmDialogModule,
    ToastModule,
    GoogleMapsModule,
    GoogleMap,
    MessagesModule,
    InputIconModule,
    IconFieldModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  exports: [RouterModule],
  providers: [
    httpInterceptorProviders,
    AuthService,
    StorageService,
    UserService,
    APIStore,
    APIService,
    ReviewService,
    ConfirmationService,
    MessageService,
    WebSocketService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
