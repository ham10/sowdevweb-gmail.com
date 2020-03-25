import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtagere } from 'app/shared/model/etagere.model';

@Component({
  selector: 'jhi-etagere-detail',
  templateUrl: './etagere-detail.component.html'
})
export class EtagereDetailComponent implements OnInit {
  etagere: IEtagere | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etagere }) => (this.etagere = etagere));
  }

  previousState(): void {
    window.history.back();
  }
}
