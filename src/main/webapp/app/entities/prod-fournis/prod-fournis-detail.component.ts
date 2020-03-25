import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProdFournis } from 'app/shared/model/prod-fournis.model';

@Component({
  selector: 'jhi-prod-fournis-detail',
  templateUrl: './prod-fournis-detail.component.html'
})
export class ProdFournisDetailComponent implements OnInit {
  prodFournis: IProdFournis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prodFournis }) => (this.prodFournis = prodFournis));
  }

  previousState(): void {
    window.history.back();
  }
}
