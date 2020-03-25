import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEcheancier } from 'app/shared/model/echeancier.model';

@Component({
  selector: 'jhi-echeancier-detail',
  templateUrl: './echeancier-detail.component.html'
})
export class EcheancierDetailComponent implements OnInit {
  echeancier: IEcheancier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ echeancier }) => (this.echeancier = echeancier));
  }

  previousState(): void {
    window.history.back();
  }
}
