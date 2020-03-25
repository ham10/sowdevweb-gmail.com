import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeCaisse } from 'app/shared/model/type-caisse.model';

@Component({
  selector: 'jhi-type-caisse-detail',
  templateUrl: './type-caisse-detail.component.html'
})
export class TypeCaisseDetailComponent implements OnInit {
  typeCaisse: ITypeCaisse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCaisse }) => (this.typeCaisse = typeCaisse));
  }

  previousState(): void {
    window.history.back();
  }
}
