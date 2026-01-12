import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthenticationService } from '../../service/authentication.service';
import { Router, RouterLink } from '@angular/router';
@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule,CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm: FormGroup;
  invalidCredential: boolean = false;
  constructor(private fb: FormBuilder,
     private authService: AuthenticationService,
     private router: Router
    ) {
    this.loginForm = fb.group({
      username: ['', [Validators.required]],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      console.log('Login submitted', this.loginForm.value);
      
      this.authService.login(this.loginForm.value).subscribe(
        (res)=>{
          localStorage.setItem('jwt',res.jwt);
          this.router.navigateByUrl('/home');
        },
        ()=>{
          this.invalidCredential = true;
        }
      );
    }
  }
}
