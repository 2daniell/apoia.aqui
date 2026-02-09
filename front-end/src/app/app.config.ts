import { ApplicationConfig, inject, provideAppInitializer, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './interceptor/auth-interceptor';
import { firstValueFrom } from 'rxjs';
import { AuthService } from './services/auth/auth-service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor]) ,withFetch()),
    provideAppInitializer(() => {
      const auth = inject(AuthService);

      return firstValueFrom(
        auth.loadSession()
      );
    })
  ]
};
