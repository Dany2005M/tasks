import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import LocalStorageUtils from '../utils/localStorageUtils';

@Injectable({
  providedIn: 'root',
})
export class GuestGuard {
  private readonly router: Router = inject(Router);
  private readonly token: string | null = LocalStorageUtils.getItem(LocalStorageUtils.tokenKey);

  canActivate(): boolean {

    const token = LocalStorageUtils.getItem(LocalStorageUtils.tokenKey);
  
    if(token){
      this.router.navigate(['/home'])
      return false;
    }
    return true;
}
}
