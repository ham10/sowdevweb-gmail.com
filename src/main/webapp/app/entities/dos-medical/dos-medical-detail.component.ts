import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDosMedical } from 'app/shared/model/dos-medical.model';

@Component({
  selector: 'jhi-dos-medical-detail',
  templateUrl: './dos-medical-detail.component.html'
})
export class DosMedicalDetailComponent implements OnInit {
  dosMedical: IDosMedical | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dosMedical }) => (this.dosMedical = dosMedical));
  }

  previousState(): void {
    window.history.back();
  }
}
