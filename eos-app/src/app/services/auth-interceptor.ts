import { inject, Injectable } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import LocalStorageUtils from '../utils/localStorageUtils';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { Users } from './users';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const userService = inject(Users);

  if(req.url.includes('login') || req.url.includes('register')){
    return next(req);
  }

  const token: string | null = LocalStorageUtils.getItem(LocalStorageUtils.tokenKey);
  let processedRequest;
  if(token) {
    processedRequest = req.clone(
      {
        headers: req.headers.set('Authorization', `Bearer ${token}`),
      }
    )
  }
  else{
    processedRequest = req;
  }

  return next(processedRequest).pipe(
    catchError((error) => {
      if(error.status === 401 || error.status === 403) {
        console.warn('Expired session or access denied. Redirecting...');

        LocalStorageUtils.clear();
        
        userService.setLoggedInUser(null);

        router.navigate(['/login']);

      }
      return throwError(() => error);
    })
  );

}
