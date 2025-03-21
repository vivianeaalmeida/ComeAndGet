import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../Services/auth.service';
import { map } from 'rxjs';
import Swal from 'sweetalert2';

export const managerGuard: CanActivateFn = (route, state) => {
  if (inject(AuthService).hasToken()) {
    let teste: any;
    inject(AuthService)
      .loggedSession.pipe(map((user) => user))
      .subscribe((user) => {
        teste = user;
      });
    if (teste.roles === 'Admin') {
      return true;
    }
  }
  Swal.fire({
    icon: 'warning',
    title: 'Access denied: This page is only accessible by ADMIN!',
    footer: 'Come And Get Team',
  });
  inject(Router).navigate(['home'], { queryParams: { returnUrl: state.url } });
  return false;
};
