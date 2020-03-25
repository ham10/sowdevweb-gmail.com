import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILigneLivraison } from 'app/shared/model/ligne-livraison.model';

@Component({
  selector: 'jhi-ligne-livraison-detail',
  templateUrl: './ligne-livraison-detail.component.html'
})
export class LigneLivraisonDetailComponent implements OnInit {
  ligneLivraison: ILigneLivraison | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneLivraison }) => (this.ligneLivraison = ligneLivraison));
  }

  previousState(): void {
    window.history.back();
  }
}
