import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypePlanning } from 'app/shared/model/type-planning.model';

@Component({
  selector: 'jhi-type-planning-detail',
  templateUrl: './type-planning-detail.component.html'
})
export class TypePlanningDetailComponent implements OnInit {
  typePlanning: ITypePlanning | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePlanning }) => (this.typePlanning = typePlanning));
  }

  previousState(): void {
    window.history.back();
  }
}
