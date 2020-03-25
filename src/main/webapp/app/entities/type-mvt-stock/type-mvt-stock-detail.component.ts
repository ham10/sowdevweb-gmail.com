import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeMvtStock } from 'app/shared/model/type-mvt-stock.model';

@Component({
  selector: 'jhi-type-mvt-stock-detail',
  templateUrl: './type-mvt-stock-detail.component.html'
})
export class TypeMvtStockDetailComponent implements OnInit {
  typeMvtStock: ITypeMvtStock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeMvtStock }) => (this.typeMvtStock = typeMvtStock));
  }

  previousState(): void {
    window.history.back();
  }
}
