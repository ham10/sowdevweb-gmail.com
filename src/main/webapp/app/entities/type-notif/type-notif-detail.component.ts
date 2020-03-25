import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeNotif } from 'app/shared/model/type-notif.model';

@Component({
  selector: 'jhi-type-notif-detail',
  templateUrl: './type-notif-detail.component.html'
})
export class TypeNotifDetailComponent implements OnInit {
  typeNotif: ITypeNotif | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeNotif }) => (this.typeNotif = typeNotif));
  }

  previousState(): void {
    window.history.back();
  }
}
