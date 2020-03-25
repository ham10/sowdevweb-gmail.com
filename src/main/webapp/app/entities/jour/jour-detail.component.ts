import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJour } from 'app/shared/model/jour.model';

@Component({
  selector: 'jhi-jour-detail',
  templateUrl: './jour-detail.component.html'
})
export class JourDetailComponent implements OnInit {
  jour: IJour | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jour }) => (this.jour = jour));
  }

  previousState(): void {
    window.history.back();
  }
}
