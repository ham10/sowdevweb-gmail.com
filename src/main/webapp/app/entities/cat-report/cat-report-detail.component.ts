import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatReport } from 'app/shared/model/cat-report.model';

@Component({
  selector: 'jhi-cat-report-detail',
  templateUrl: './cat-report-detail.component.html'
})
export class CatReportDetailComponent implements OnInit {
  catReport: ICatReport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catReport }) => (this.catReport = catReport));
  }

  previousState(): void {
    window.history.back();
  }
}
