import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypePlateau } from 'app/shared/model/type-plateau.model';

@Component({
  selector: 'jhi-type-plateau-detail',
  templateUrl: './type-plateau-detail.component.html'
})
export class TypePlateauDetailComponent implements OnInit {
  typePlateau: ITypePlateau | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePlateau }) => (this.typePlateau = typePlateau));
  }

  previousState(): void {
    window.history.back();
  }
}
