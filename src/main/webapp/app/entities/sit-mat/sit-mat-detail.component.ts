import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISitMat } from 'app/shared/model/sit-mat.model';

@Component({
  selector: 'jhi-sit-mat-detail',
  templateUrl: './sit-mat-detail.component.html'
})
export class SitMatDetailComponent implements OnInit {
  sitMat: ISitMat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sitMat }) => (this.sitMat = sitMat));
  }

  previousState(): void {
    window.history.back();
  }
}
