import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHospitalisation } from 'app/shared/model/hospitalisation.model';

@Component({
  selector: 'jhi-hospitalisation-detail',
  templateUrl: './hospitalisation-detail.component.html'
})
export class HospitalisationDetailComponent implements OnInit {
  hospitalisation: IHospitalisation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospitalisation }) => (this.hospitalisation = hospitalisation));
  }

  previousState(): void {
    window.history.back();
  }
}
