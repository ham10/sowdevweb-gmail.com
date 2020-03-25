import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICivilite } from 'app/shared/model/civilite.model';

@Component({
  selector: 'jhi-civilite-detail',
  templateUrl: './civilite-detail.component.html'
})
export class CiviliteDetailComponent implements OnInit {
  civilite: ICivilite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ civilite }) => (this.civilite = civilite));
  }

  previousState(): void {
    window.history.back();
  }
}
