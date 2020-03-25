import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-navbar-menu',
  templateUrl: './navbar-menu.component.html',
  styleUrls: ['./navbar-menu.component.scss']
})
export class NavbarMenuComponent implements OnInit {
  image = '../../content/images/logo.png';
  constructor() {}

  ngOnInit(): void {}
}
