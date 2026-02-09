export interface AuthResponse {
    accessToken: string
}

export interface LoginRequest {
    email: string;
    password: string
}

export interface RegisterRequest {
    email: string
    password: string
    lastName: string
    firstName: string
    confirmPassword: string
    cpf: string
}