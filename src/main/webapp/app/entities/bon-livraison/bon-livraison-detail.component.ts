import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

@Component({
  selector: 'jhi-bon-livraison-detail',
  templateUrl: './bon-livraison-detail.component.html'
})
export class BonLivraisonDetailComponent implements OnInit {
  bonLivraison: IBonLivraison | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonLivraison }) => (this.bonLivraison = bonLivraison));
  }

  previousState(): void {
    window.history.back();
  }
}
