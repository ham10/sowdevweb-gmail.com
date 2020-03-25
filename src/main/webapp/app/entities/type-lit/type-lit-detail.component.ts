import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeLit } from 'app/shared/model/type-lit.model';

@Component({
  selector: 'jhi-type-lit-detail',
  templateUrl: './type-lit-detail.component.html'
})
export class TypeLitDetailComponent implements OnInit {
  typeLit: ITypeLit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeLit }) => (this.typeLit = typeLit));
  }

  previousState(): void {
    window.history.back();
  }
}
