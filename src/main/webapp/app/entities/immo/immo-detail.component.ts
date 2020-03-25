import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImmo } from 'app/shared/model/immo.model';

@Component({
  selector: 'jhi-immo-detail',
  templateUrl: './immo-detail.component.html'
})
export class ImmoDetailComponent implements OnInit {
  immo: IImmo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ immo }) => (this.immo = immo));
  }

  previousState(): void {
    window.history.back();
  }
}
