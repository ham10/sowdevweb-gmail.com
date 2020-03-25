import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILit } from 'app/shared/model/lit.model';

@Component({
  selector: 'jhi-lit-detail',
  templateUrl: './lit-detail.component.html'
})
export class LitDetailComponent implements OnInit {
  lit: ILit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lit }) => (this.lit = lit));
  }

  previousState(): void {
    window.history.back();
  }
}
