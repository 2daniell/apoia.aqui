import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth/auth-service';

const PUBLIC_API_ENDPOINTS = [
  '/auth/signin',
  '/auth/signup',
  '/auth/refresh',
];

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const auth = inject(AuthService);

  if (PUBLIC_API_ENDPOINTS.some(url => req.url.includes(url))) {
    return next(req);
  }

  const token = auth.getAcessToken();

  if (!token) return next(req);

  const authRequest = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  })

  return next(authRequest);
};
