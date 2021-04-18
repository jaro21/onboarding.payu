import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: string = localStorage.getItem('token') || '';
    const uri_auth = '/shop/v1.0/authenticate';
    //const uri_get_prod = '/shop/v1.0/products';

    console.log(req);

    let request = req;

    //if (token && uri_auth != req.url && (uri_get_prod == req.url && req.method != 'GET'))  {
    if (token && uri_auth != req.url)  {
      request = req.clone({
        setHeaders: {
          Authorization: `Bearer ${ token }`
        }
      });
    }

    return next.handle(request).pipe(
      catchError((err: HttpErrorResponse) => {

        if (err.status === 401) {
          this.router.navigateByUrl('/list');
        }

        return throwError( err );

      })
    );
  }
  
}
