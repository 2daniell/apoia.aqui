import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { User } from '../../model/user-model';
import { Observable, of, switchMap, tap } from 'rxjs';
import { LoginResponse } from '../../model/aurh-model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly BASE_URL = "http://localhost:8080/api/auth";
  private readonly httpClient = inject(HttpClient);

  public user = signal<User | null>(null);
  public accessToken = signal<string | null>(null);

  public login(email: string, password: string) {
    return this.httpClient.post<LoginResponse>(`${this.BASE_URL}/signin`, { password, email }, { withCredentials: true})
      .pipe(tap(res => {
        this.accessToken.set(res.accessToken)

        localStorage.setItem("token", res.accessToken)

    }),
      switchMap(() => this.getMe())
    )
  }

  public register(cpf: string, firtName: string, lastName: string, email: string, password: string, confirmPassword: string) {
    return this.httpClient.post<LoginResponse>(`${this.BASE_URL}/signup`, { cpf, firtName, lastName, email, password, confirmPassword }, { withCredentials: true})
  }

  public restoreSession(): Observable<User | null> {
    const token = localStorage.getItem("token");

    console.log("ANTES")

    if (!token) return of(null);

    console.log("PASOU")

    this.accessToken.set(token);

    return this.getMe();
  }

  public isLogged() {
    return !!this.accessToken();
  }

  public getMe() {
    const token = this.accessToken();

    if (!token) {
      return of(null);
    }

    return this.httpClient.get<User>(`${this.BASE_URL}/me`, { headers: { Authorization: `Bearer ${token}` }, withCredentials: true })
      .pipe(
        tap(user => {
          this.user.set(user);
          console.log(user)
          console.log(user.name, user.email, user.id)
      }),
    );
  }
  
}
