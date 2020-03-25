import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITauxDevise } from 'app/shared/model/taux-devise.model';

@Component({
  selector: 'jhi-taux-devise-detail',
  templateUrl: './taux-devise-detail.component.html'
})
export class TauxDeviseDetailComponent implements OnInit {
  tauxDevise: ITauxDevise | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tauxDevise }) => (this.tauxDevise = tauxDevise));
  }

  previousState(): void {
    window.history.back();
  }
}
