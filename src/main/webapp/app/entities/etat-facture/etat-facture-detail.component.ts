import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatFacture } from 'app/shared/model/etat-facture.model';

@Component({
  selector: 'jhi-etat-facture-detail',
  templateUrl: './etat-facture-detail.component.html'
})
export class EtatFactureDetailComponent implements OnInit {
  etatFacture: IEtatFacture | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatFacture }) => (this.etatFacture = etatFacture));
  }

  previousState(): void {
    window.history.back();
  }
}
