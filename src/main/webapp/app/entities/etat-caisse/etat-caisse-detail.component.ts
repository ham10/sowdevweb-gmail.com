import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatCaisse } from 'app/shared/model/etat-caisse.model';

@Component({
  selector: 'jhi-etat-caisse-detail',
  templateUrl: './etat-caisse-detail.component.html'
})
export class EtatCaisseDetailComponent implements OnInit {
  etatCaisse: IEtatCaisse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatCaisse }) => (this.etatCaisse = etatCaisse));
  }

  previousState(): void {
    window.history.back();
  }
}
