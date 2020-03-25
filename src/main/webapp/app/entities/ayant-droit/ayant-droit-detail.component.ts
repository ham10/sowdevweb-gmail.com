import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAyantDroit } from 'app/shared/model/ayant-droit.model';

@Component({
  selector: 'jhi-ayant-droit-detail',
  templateUrl: './ayant-droit-detail.component.html'
})
export class AyantDroitDetailComponent implements OnInit {
  ayantDroit: IAyantDroit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ayantDroit }) => (this.ayantDroit = ayantDroit));
  }

  previousState(): void {
    window.history.back();
  }
}
