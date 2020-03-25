import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeImmo } from 'app/shared/model/type-immo.model';

@Component({
  selector: 'jhi-type-immo-detail',
  templateUrl: './type-immo-detail.component.html'
})
export class TypeImmoDetailComponent implements OnInit {
  typeImmo: ITypeImmo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeImmo }) => (this.typeImmo = typeImmo));
  }

  previousState(): void {
    window.history.back();
  }
}
