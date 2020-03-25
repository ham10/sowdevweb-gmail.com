import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeReport } from 'app/shared/model/type-report.model';

@Component({
  selector: 'jhi-type-report-detail',
  templateUrl: './type-report-detail.component.html'
})
export class TypeReportDetailComponent implements OnInit {
  typeReport: ITypeReport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeReport }) => (this.typeReport = typeReport));
  }

  previousState(): void {
    window.history.back();
  }
}
