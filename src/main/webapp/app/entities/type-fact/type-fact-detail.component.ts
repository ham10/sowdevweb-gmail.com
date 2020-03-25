import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeFact } from 'app/shared/model/type-fact.model';

@Component({
  selector: 'jhi-type-fact-detail',
  templateUrl: './type-fact-detail.component.html'
})
export class TypeFactDetailComponent implements OnInit {
  typeFact: ITypeFact | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeFact }) => (this.typeFact = typeFact));
  }

  previousState(): void {
    window.history.back();
  }
}
