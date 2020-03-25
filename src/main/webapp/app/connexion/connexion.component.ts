import { Component, OnInit, Renderer } from '@angular/core';
import { LoginService } from 'app/core/login/login.service';
import { Router } from '@angular/router';
// import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import { FormBuilder } from '@angular/forms';
// import {Account} from "app/core/user/account.model";
// import {Subscription} from "rxjs";
// import {AccountService} from "app/core/auth/account.service";

@Component({
  selector: 'jhi-connexion',
  templateUrl: './connexion.component.html',
  styleUrls: ['./connexion.component.scss']
})
export class ConnexionComponent implements OnInit {
  image = '../../content/images/logo.png';
  loginForm = this.fb.group({
    username: [''],
    password: ['']
    // rememberMe: [false]
  });
  // account: Account | null = null;
  // authSubscription?: Subscription;
  authenticationError = false;
  constructor(
    private loginService: LoginService,
    private renderer: Renderer,
    private router: Router,
    private fb: FormBuilder
  ) // private accountService: AccountService
  {}

  ngOnInit(): void {
    // this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  // isAuthenticated(): boolean {
  //   return this.accountService.isAuthenticated();
  // }

  // cancel(): void {
  //   this.authenticationError = false;
  //   this.loginForm.patchValue({
  //     username: '',
  //     password: ''
  //   });
  //   // this.activeModal.dismiss('cancel');
  // }

  login(): void {
    this.loginService
      .login({
        username: this.loginForm.get('username')!.value,
        password: this.loginForm.get('password')!.value,
        rememberMe: false
      })
      .subscribe(
        () => {
          this.authenticationError = false;
          this.router.navigateByUrl('/menu');
          if (
            this.router.url === '/account/register' ||
            this.router.url.startsWith('/account/activate') ||
            this.router.url.startsWith('/account/reset/')
          ) {
            this.router.navigateByUrl('/menu');
          }
        },
        () => (this.authenticationError = true)
      );
  }
}
