import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { inject } from '@angular/core';
import { map, take } from 'rxjs';
import Swal from 'sweetalert2';

export const clientGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // If the user is not authenticated, they can't access the
  if (!authService.hasToken()) {
    Swal.fire({
      icon: 'warning',
      title: 'Access denied: This page is only accessible by registered users!',
      footer: 'Come And Get Team',
    });
    router.navigate(['home'], { queryParams: { returnUrl: state.url } });
    return false;
  }

  // If the user doesn't have the 'User' role, they can't access the page
  return authService.loggedSession.pipe(
    take(1),
    map((user) => {
      console.log('Authenticated user:', user);
      if (user?.roles === 'User') {
        return true;
      }

      Swal.fire({
        icon: 'warning',
        title:
          'Access denied: This page is only accessible by the ones with user role!',
        footer: 'Come And Get Team',
      });

      router.navigate(['home'], { queryParams: { returnUrl: state.url } });
      return false;
    })
  );
};
