import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBoxe } from 'app/shared/model/boxe.model';

@Component({
  selector: 'jhi-boxe-detail',
  templateUrl: './boxe-detail.component.html'
})
export class BoxeDetailComponent implements OnInit {
  boxe: IBoxe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boxe }) => (this.boxe = boxe));
  }

  previousState(): void {
    window.history.back();
  }
}
