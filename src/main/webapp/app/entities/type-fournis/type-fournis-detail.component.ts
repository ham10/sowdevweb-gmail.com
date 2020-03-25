import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeFournis } from 'app/shared/model/type-fournis.model';

@Component({
  selector: 'jhi-type-fournis-detail',
  templateUrl: './type-fournis-detail.component.html'
})
export class TypeFournisDetailComponent implements OnInit {
  typeFournis: ITypeFournis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeFournis }) => (this.typeFournis = typeFournis));
  }

  previousState(): void {
    window.history.back();
  }
}
