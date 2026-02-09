import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth-service';
import { LoginRequest } from '../../model/aurh-model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {

  public form: FormGroup;

  private auth = inject(AuthService);
  private router = inject(Router)

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  get email() {
    return this.form.get('email');
  }

  get password() {
    return this.form.get('password');
  }


  public submit() {
    if (this.form.invalid) return;
    
    const data: LoginRequest = this.form.getRawValue();

    this.auth.login(data).subscribe({
      next: () => this.router.navigate(["/app/dashboard"]),
      error: () => alert("Ocorreu um erro")
    })
  }
}
