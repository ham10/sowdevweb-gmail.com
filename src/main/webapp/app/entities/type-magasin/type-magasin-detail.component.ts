import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeMagasin } from 'app/shared/model/type-magasin.model';

@Component({
  selector: 'jhi-type-magasin-detail',
  templateUrl: './type-magasin-detail.component.html'
})
export class TypeMagasinDetailComponent implements OnInit {
  typeMagasin: ITypeMagasin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeMagasin }) => (this.typeMagasin = typeMagasin));
  }

  previousState(): void {
    window.history.back();
  }
}
