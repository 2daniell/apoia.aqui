import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from '../../model/aurh-model';
import { AuthService } from '../../services/auth/auth-service';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css',
})
export class Cadastro {

  form: FormGroup;

  private service = inject(AuthService)

  constructor(private fb: FormBuilder,private router: Router) {
    this.form = this.fb.group({
      cpf: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
    });
  }

  get nome() {
    return this.form.get('nome');
  }

  get firstName() {
    return this.form.get("firstName");
  }

  get lastName() {
    return this.form.get("lastName");
  }

  get cpf() {
    return this.form.get('cpf');
  }

  get email() {
    return this.form.get('email');
  }

  get password() {
    return this.form.get('password');
  }

  get confirmPassword() {
    return this.form.get('confirmPassword');
  }

    voltarLogin() {
    this.router.navigate(['/login']);
  }

    enviar() {
    if (this.form.invalid) return;

    if (this.password?.value !== this.confirmPassword?.value) {
      this.confirmPassword?.setErrors({ mismatch: true });
      return;
    }

    const data: RegisterRequest = this.form.getRawValue();

    this.service.register(data).subscribe(() => this.router.navigate(["/app/dashboard"]))
  }

}
