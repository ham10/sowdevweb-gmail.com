import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeSoins } from 'app/shared/model/type-soins.model';

@Component({
  selector: 'jhi-type-soins-detail',
  templateUrl: './type-soins-detail.component.html'
})
export class TypeSoinsDetailComponent implements OnInit {
  typeSoins: ITypeSoins | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeSoins }) => (this.typeSoins = typeSoins));
  }

  previousState(): void {
    window.history.back();
  }
}
