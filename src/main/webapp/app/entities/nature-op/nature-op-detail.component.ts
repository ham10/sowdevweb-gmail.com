import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureOp } from 'app/shared/model/nature-op.model';

@Component({
  selector: 'jhi-nature-op-detail',
  templateUrl: './nature-op-detail.component.html'
})
export class NatureOpDetailComponent implements OnInit {
  natureOp: INatureOp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureOp }) => (this.natureOp = natureOp));
  }

  previousState(): void {
    window.history.back();
  }
}
