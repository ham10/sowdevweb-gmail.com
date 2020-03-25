import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeSortie } from 'app/shared/model/type-sortie.model';

@Component({
  selector: 'jhi-type-sortie-detail',
  templateUrl: './type-sortie-detail.component.html'
})
export class TypeSortieDetailComponent implements OnInit {
  typeSortie: ITypeSortie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeSortie }) => (this.typeSortie = typeSortie));
  }

  previousState(): void {
    window.history.back();
  }
}
