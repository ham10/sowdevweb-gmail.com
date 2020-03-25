import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeCond } from 'app/shared/model/type-cond.model';

@Component({
  selector: 'jhi-type-cond-detail',
  templateUrl: './type-cond-detail.component.html'
})
export class TypeCondDetailComponent implements OnInit {
  typeCond: ITypeCond | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCond }) => (this.typeCond = typeCond));
  }

  previousState(): void {
    window.history.back();
  }
}
