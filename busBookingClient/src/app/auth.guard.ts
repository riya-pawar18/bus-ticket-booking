import { CanActivateFn } from '@angular/router';

import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthserviceService } from './auth/authservice.service';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthserviceService);
  const router = inject(Router);

  let loggedIn = false;

  auth.isLoggedIn$.subscribe((v) => (loggedIn = v)).unsubscribe();

  if (loggedIn) return true;

  router.navigate(['/login']);
  return false;
};
