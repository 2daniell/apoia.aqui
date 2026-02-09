import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { AuthResponse, LoginRequest, RegisterRequest } from '../../model/aurh-model';
import { catchError, of, switchMap, tap } from 'rxjs';
import { User } from '../../model/user-model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly BASE_URL = "http://localhost:8080/api/auth";
  private readonly httpClient = inject(HttpClient);

  public user = signal<User | null>(null);

  public isAuthenticated = computed(() => !!this.user());

  private accessToken?: string

  public login(data: LoginRequest) {
    return this.httpClient.post<AuthResponse>(`${this.BASE_URL}/signin`, data, { withCredentials: true }).pipe(
      tap(res => this.setAcessToken(res.accessToken)),
      switchMap(() => this.getMe())
    );
  }

  public register(data: RegisterRequest) {
    return this.httpClient.post<AuthResponse>(`${this.BASE_URL}/signup`, data, { withCredentials: true }).pipe(
      tap(res => this.setAcessToken(res.accessToken)),
      switchMap(() => this.getMe())
    );
  }
  
  public refresh() {
    return this.httpClient.post<AuthResponse>(`${this.BASE_URL}/refresh`, {}, { withCredentials: true }).pipe(
      tap(res => {
        this.setAcessToken(res.accessToken)
      }),
      switchMap(() => this.getMe())
    )
  }

  public logout() {
    return this.httpClient.post(`${this.BASE_URL}/logout`, {}, { withCredentials: true }).pipe(tap(() => {
      this.user.set(null);
      this.accessToken = undefined;
    }))
  }

  public loadSession() {
    return this.refresh().pipe(catchError(() => of(null)));
  }

  public getAcessToken(): string | undefined {
    return this.accessToken;
  }

  private getMe() {
    return this.httpClient.get<User>(`${this.BASE_URL}/me`, { withCredentials: true }).pipe(
      tap(res => this.user.set(res))
    )
  }

  private setAcessToken(token: string) {
    this.accessToken = token;
  }
}
