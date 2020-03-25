import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeConstante } from 'app/shared/model/type-constante.model';

@Component({
  selector: 'jhi-type-constante-detail',
  templateUrl: './type-constante-detail.component.html'
})
export class TypeConstanteDetailComponent implements OnInit {
  typeConstante: ITypeConstante | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeConstante }) => (this.typeConstante = typeConstante));
  }

  previousState(): void {
    window.history.back();
  }
}
