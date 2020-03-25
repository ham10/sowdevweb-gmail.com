import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeChamps } from 'app/shared/model/type-champs.model';

@Component({
  selector: 'jhi-type-champs-detail',
  templateUrl: './type-champs-detail.component.html'
})
export class TypeChampsDetailComponent implements OnInit {
  typeChamps: ITypeChamps | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeChamps }) => (this.typeChamps = typeChamps));
  }

  previousState(): void {
    window.history.back();
  }
}
