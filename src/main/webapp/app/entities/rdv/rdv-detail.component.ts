import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRDV } from 'app/shared/model/rdv.model';

@Component({
  selector: 'jhi-rdv-detail',
  templateUrl: './rdv-detail.component.html'
})
export class RDVDetailComponent implements OnInit {
  rDV: IRDV | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rDV }) => (this.rDV = rDV));
  }

  previousState(): void {
    window.history.back();
  }
}
