import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatBonCom } from 'app/shared/model/etat-bon-com.model';

@Component({
  selector: 'jhi-etat-bon-com-detail',
  templateUrl: './etat-bon-com-detail.component.html'
})
export class EtatBonComDetailComponent implements OnInit {
  etatBonCom: IEtatBonCom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatBonCom }) => (this.etatBonCom = etatBonCom));
  }

  previousState(): void {
    window.history.back();
  }
}
