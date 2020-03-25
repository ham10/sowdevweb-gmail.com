import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailPlanning } from 'app/shared/model/detail-planning.model';

@Component({
  selector: 'jhi-detail-planning-detail',
  templateUrl: './detail-planning-detail.component.html'
})
export class DetailPlanningDetailComponent implements OnInit {
  detailPlanning: IDetailPlanning | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailPlanning }) => (this.detailPlanning = detailPlanning));
  }

  previousState(): void {
    window.history.back();
  }
}
