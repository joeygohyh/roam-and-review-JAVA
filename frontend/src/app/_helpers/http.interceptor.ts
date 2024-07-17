import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

// intercepts outgoing HTTP requests and modifies them before they are sent
@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>, // represents the outgoing HTTP request
    next: HttpHandler // represents the next interceptor in the chain / the final HttpBackend for sending the request
  ): Observable<HttpEvent<any>> {
    req = req.clone({ // creates a clone of the original request (req) with modifications
      withCredentials: true,
    });

    return next.handle(req);
    //passes the modified request (req) to the next interceptor in the chain or sends it to the server if this interceptor is the last one
  }
}

// an array that provides the configuration for Angular HTTP interceptors
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
];
