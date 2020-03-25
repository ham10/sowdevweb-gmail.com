import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatPlanning } from 'app/shared/model/etat-planning.model';

@Component({
  selector: 'jhi-etat-planning-detail',
  templateUrl: './etat-planning-detail.component.html'
})
export class EtatPlanningDetailComponent implements OnInit {
  etatPlanning: IEtatPlanning | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatPlanning }) => (this.etatPlanning = etatPlanning));
  }

  previousState(): void {
    window.history.back();
  }
}
