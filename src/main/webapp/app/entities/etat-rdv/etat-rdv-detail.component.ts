import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatRdv } from 'app/shared/model/etat-rdv.model';

@Component({
  selector: 'jhi-etat-rdv-detail',
  templateUrl: './etat-rdv-detail.component.html'
})
export class EtatRdvDetailComponent implements OnInit {
  etatRdv: IEtatRdv | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatRdv }) => (this.etatRdv = etatRdv));
  }

  previousState(): void {
    window.history.back();
  }
}
