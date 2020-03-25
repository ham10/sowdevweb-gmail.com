import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalendrier } from 'app/shared/model/calendrier.model';

@Component({
  selector: 'jhi-calendrier-detail',
  templateUrl: './calendrier-detail.component.html'
})
export class CalendrierDetailComponent implements OnInit {
  calendrier: ICalendrier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calendrier }) => (this.calendrier = calendrier));
  }

  previousState(): void {
    window.history.back();
  }
}
