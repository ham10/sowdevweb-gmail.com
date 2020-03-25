import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatOperation } from 'app/shared/model/etat-operation.model';

@Component({
  selector: 'jhi-etat-operation-detail',
  templateUrl: './etat-operation-detail.component.html'
})
export class EtatOperationDetailComponent implements OnInit {
  etatOperation: IEtatOperation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etatOperation }) => (this.etatOperation = etatOperation));
  }

  previousState(): void {
    window.history.back();
  }
}
