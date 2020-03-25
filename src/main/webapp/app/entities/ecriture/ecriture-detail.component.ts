import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEcriture } from 'app/shared/model/ecriture.model';

@Component({
  selector: 'jhi-ecriture-detail',
  templateUrl: './ecriture-detail.component.html'
})
export class EcritureDetailComponent implements OnInit {
  ecriture: IEcriture | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecriture }) => (this.ecriture = ecriture));
  }

  previousState(): void {
    window.history.back();
  }
}
