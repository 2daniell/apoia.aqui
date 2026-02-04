import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth-service';
import { LoginRequest } from '../../model/aurh-model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {

  public form: FormGroup;

  private auth = inject(AuthService);
  private routr = inject(Router)

  constructor(private fb: FormBuilder, private router: Router) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  get email() {
    return this.form.get('email');
  }

  get senha() {
    return this.form.get('password');
  }

  irParaCadastro() {
      this.router.navigate(['/cadastro']);
  }

  enviar() {
    if (this.form.invalid) return;
    
    const { email, password }: LoginRequest = this.form.getRawValue();

    this.auth.login(email, password).subscribe({
      next: () => this.router.navigate(["/app/dashboard"]),
      error: () => alert("Ocorreu um erro")
    })
  }
}
