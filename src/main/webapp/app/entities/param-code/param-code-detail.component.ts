import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParamCode } from 'app/shared/model/param-code.model';

@Component({
  selector: 'jhi-param-code-detail',
  templateUrl: './param-code-detail.component.html'
})
export class ParamCodeDetailComponent implements OnInit {
  paramCode: IParamCode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paramCode }) => (this.paramCode = paramCode));
  }

  previousState(): void {
    window.history.back();
  }
}
