import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModeRegle } from 'app/shared/model/mode-regle.model';

@Component({
  selector: 'jhi-mode-regle-detail',
  templateUrl: './mode-regle-detail.component.html'
})
export class ModeRegleDetailComponent implements OnInit {
  modeRegle: IModeRegle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modeRegle }) => (this.modeRegle = modeRegle));
  }

  previousState(): void {
    window.history.back();
  }
}
