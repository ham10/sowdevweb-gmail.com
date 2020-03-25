import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeTarif } from 'app/shared/model/type-tarif.model';

@Component({
  selector: 'jhi-type-tarif-detail',
  templateUrl: './type-tarif-detail.component.html'
})
export class TypeTarifDetailComponent implements OnInit {
  typeTarif: ITypeTarif | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeTarif }) => (this.typeTarif = typeTarif));
  }

  previousState(): void {
    window.history.back();
  }
}
