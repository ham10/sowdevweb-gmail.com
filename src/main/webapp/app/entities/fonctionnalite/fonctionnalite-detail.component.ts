import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFonctionnalite } from 'app/shared/model/fonctionnalite.model';

@Component({
  selector: 'jhi-fonctionnalite-detail',
  templateUrl: './fonctionnalite-detail.component.html'
})
export class FonctionnaliteDetailComponent implements OnInit {
  fonctionnalite: IFonctionnalite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fonctionnalite }) => (this.fonctionnalite = fonctionnalite));
  }

  previousState(): void {
    window.history.back();
  }
}
