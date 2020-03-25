import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVaccin } from 'app/shared/model/vaccin.model';

@Component({
  selector: 'jhi-vaccin-detail',
  templateUrl: './vaccin-detail.component.html'
})
export class VaccinDetailComponent implements OnInit {
  vaccin: IVaccin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vaccin }) => (this.vaccin = vaccin));
  }

  previousState(): void {
    window.history.back();
  }
}
