import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParamDivers } from 'app/shared/model/param-divers.model';

@Component({
  selector: 'jhi-param-divers-detail',
  templateUrl: './param-divers-detail.component.html'
})
export class ParamDiversDetailComponent implements OnInit {
  paramDivers: IParamDivers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paramDivers }) => (this.paramDivers = paramDivers));
  }

  previousState(): void {
    window.history.back();
  }
}
