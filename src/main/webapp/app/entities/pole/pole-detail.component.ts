import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPole } from 'app/shared/model/pole.model';

@Component({
  selector: 'jhi-pole-detail',
  templateUrl: './pole-detail.component.html'
})
export class PoleDetailComponent implements OnInit {
  pole: IPole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pole }) => (this.pole = pole));
  }

  previousState(): void {
    window.history.back();
  }
}
