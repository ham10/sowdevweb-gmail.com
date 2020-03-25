import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChapCompta } from 'app/shared/model/chap-compta.model';

@Component({
  selector: 'jhi-chap-compta-detail',
  templateUrl: './chap-compta-detail.component.html'
})
export class ChapComptaDetailComponent implements OnInit {
  chapCompta: IChapCompta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chapCompta }) => (this.chapCompta = chapCompta));
  }

  previousState(): void {
    window.history.back();
  }
}
