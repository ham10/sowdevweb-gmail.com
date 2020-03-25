import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeUnite } from 'app/shared/model/type-unite.model';

@Component({
  selector: 'jhi-type-unite-detail',
  templateUrl: './type-unite-detail.component.html'
})
export class TypeUniteDetailComponent implements OnInit {
  typeUnite: ITypeUnite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeUnite }) => (this.typeUnite = typeUnite));
  }

  previousState(): void {
    window.history.back();
  }
}
