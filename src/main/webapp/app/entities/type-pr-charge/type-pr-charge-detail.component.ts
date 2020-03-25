import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypePrCharge } from 'app/shared/model/type-pr-charge.model';

@Component({
  selector: 'jhi-type-pr-charge-detail',
  templateUrl: './type-pr-charge-detail.component.html'
})
export class TypePrChargeDetailComponent implements OnInit {
  typePrCharge: ITypePrCharge | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePrCharge }) => (this.typePrCharge = typePrCharge));
  }

  previousState(): void {
    window.history.back();
  }
}
