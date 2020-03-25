import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatImmo } from 'app/shared/model/etat-immo.model';

@Component({
  selector: 'jhi-etat-immo-detail',
  templateUrl: './etat-immo-detail.component.html'
})
export class EtatImmoDetailComponent implements OnInit {
  etatImmo: IEtatImmo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatImmo }) => (this.etatImmo = etatImmo));
  }

  previousState(): void {
    window.history.back();
  }
}
