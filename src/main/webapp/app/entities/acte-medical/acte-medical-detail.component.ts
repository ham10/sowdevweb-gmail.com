import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActeMedical } from 'app/shared/model/acte-medical.model';

@Component({
  selector: 'jhi-acte-medical-detail',
  templateUrl: './acte-medical-detail.component.html'
})
export class ActeMedicalDetailComponent implements OnInit {
  acteMedical: IActeMedical | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ acteMedical }) => (this.acteMedical = acteMedical));
  }

  previousState(): void {
    window.history.back();
  }
}
