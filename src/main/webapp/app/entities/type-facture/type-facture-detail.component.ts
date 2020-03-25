import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeFacture } from 'app/shared/model/type-facture.model';

@Component({
  selector: 'jhi-type-facture-detail',
  templateUrl: './type-facture-detail.component.html'
})
export class TypeFactureDetailComponent implements OnInit {
  typeFacture: ITypeFacture | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeFacture }) => (this.typeFacture = typeFacture));
  }

  previousState(): void {
    window.history.back();
  }
}
