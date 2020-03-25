import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParamSys } from 'app/shared/model/param-sys.model';

@Component({
  selector: 'jhi-param-sys-detail',
  templateUrl: './param-sys-detail.component.html'
})
export class ParamSysDetailComponent implements OnInit {
  paramSys: IParamSys | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paramSys }) => (this.paramSys = paramSys));
  }

  previousState(): void {
    window.history.back();
  }
}
