import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtablis } from 'app/shared/model/etablis.model';

@Component({
  selector: 'jhi-etablis-detail',
  templateUrl: './etablis-detail.component.html'
})
export class EtablisDetailComponent implements OnInit {
  etablis: IEtablis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablis }) => (this.etablis = etablis));
  }

  previousState(): void {
    window.history.back();
  }
}
