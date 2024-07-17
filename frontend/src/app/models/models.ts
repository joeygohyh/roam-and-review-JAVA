export interface SearchResult {
  date: number;
  q: string;
  parks: Park[];
}


export interface Park {
  id: string; // can omit
  url: string; // can omit
  fullName: string;
  parkCode: string;
  description: string;
  image: string; // URL of the first image
}

export interface FullPark {
  id: string;
  url: string;
  fullName: string;
  parkCode: string;
  description: string;
  latitude: string;
  longitude: string;
  latLong: string;
  operatingHours: OperatingHours;
  address: Address;
  images: Image[];
}

export interface OperatingHours {
  description: string;
  monday: string;
  tuesday: string;
  wednesday: string;
  thursday: string;
  friday: string;
  saturday: string;
  sunday: string;
}

export interface Address {
  postalCode: string;
  city: string;
  stateCode: string;
  countryCode: string;
  line1: string;
  line2: string;
  line3: string;
}

export interface Image {
  credit: string;
  title: string;
  altText: string;
  caption: string;
  url: string;
}

export interface SearchResultSlice {
  results: SearchResult[];
}

export interface Activity {
  title: string;
  shortDescription: string;
  // latitude: string;
  // longitude: string;
  // latLong: string;
  image: string;
}

export interface VisitorCentre {
  name: string;
  description: string;
  latitude: string;
  longitude: string;
  latLong: string;
  image: string;
}

export interface Campground {
  name: string;
  description: string;
  latitude: string;
  longitude: string;
  latLong: string;
  image: string;
}

export interface Review {
  id: number;
  username: string;
  // profileUrl: string;
  review: string;
  location: string;
  reviewUrl: string;
  parkCode: string;

}

export interface Review {
  id: number;
  name: string;
  username: string;
  email: string;
  password: string;
  country: string;
  gender: string;
  parkCode: string;
  likes: number;
  likedBy: String[];
  // liked: boolean;

}

export interface Account{
  id : number;
  name : string;
  username : string;
  email : string;
  password : string;
  gender : string;
  country : string;
  profileUrl : string;
}

export interface Message{
  name: string;
  content: string;
  date: Date;
}