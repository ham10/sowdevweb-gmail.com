import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatMagasin } from 'app/shared/model/cat-magasin.model';

@Component({
  selector: 'jhi-cat-magasin-detail',
  templateUrl: './cat-magasin-detail.component.html'
})
export class CatMagasinDetailComponent implements OnInit {
  catMagasin: ICatMagasin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catMagasin }) => (this.catMagasin = catMagasin));
  }

  previousState(): void {
    window.history.back();
  }
}
