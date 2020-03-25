import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeptServices } from 'app/shared/model/dept-services.model';

@Component({
  selector: 'jhi-dept-services-detail',
  templateUrl: './dept-services-detail.component.html'
})
export class DeptServicesDetailComponent implements OnInit {
  deptServices: IDeptServices | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deptServices }) => (this.deptServices = deptServices));
  }

  previousState(): void {
    window.history.back();
  }
}
