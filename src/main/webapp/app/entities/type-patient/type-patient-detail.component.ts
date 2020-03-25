import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypePatient } from 'app/shared/model/type-patient.model';

@Component({
  selector: 'jhi-type-patient-detail',
  templateUrl: './type-patient-detail.component.html'
})
export class TypePatientDetailComponent implements OnInit {
  typePatient: ITypePatient | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePatient }) => (this.typePatient = typePatient));
  }

  previousState(): void {
    window.history.back();
  }
}
