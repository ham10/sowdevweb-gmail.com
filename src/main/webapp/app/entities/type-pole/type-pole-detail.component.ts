import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypePole } from 'app/shared/model/type-pole.model';

@Component({
  selector: 'jhi-type-pole-detail',
  templateUrl: './type-pole-detail.component.html'
})
export class TypePoleDetailComponent implements OnInit {
  typePole: ITypePole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePole }) => (this.typePole = typePole));
  }

  previousState(): void {
    window.history.back();
  }
}
