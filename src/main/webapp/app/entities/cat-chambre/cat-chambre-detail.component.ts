import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatChambre } from 'app/shared/model/cat-chambre.model';

@Component({
  selector: 'jhi-cat-chambre-detail',
  templateUrl: './cat-chambre-detail.component.html'
})
export class CatChambreDetailComponent implements OnInit {
  catChambre: ICatChambre | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catChambre }) => (this.catChambre = catChambre));
  }

  previousState(): void {
    window.history.back();
  }
}
