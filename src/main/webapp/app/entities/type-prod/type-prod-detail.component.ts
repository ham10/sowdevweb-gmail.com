import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeProd } from 'app/shared/model/type-prod.model';

@Component({
  selector: 'jhi-type-prod-detail',
  templateUrl: './type-prod-detail.component.html'
})
export class TypeProdDetailComponent implements OnInit {
  typeProd: ITypeProd | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeProd }) => (this.typeProd = typeProd));
  }

  previousState(): void {
    window.history.back();
  }
}
