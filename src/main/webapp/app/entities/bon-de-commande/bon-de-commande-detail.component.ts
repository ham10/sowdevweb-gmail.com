import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';

@Component({
  selector: 'jhi-bon-de-commande-detail',
  templateUrl: './bon-de-commande-detail.component.html'
})
export class BonDeCommandeDetailComponent implements OnInit {
  bonDeCommande: IBonDeCommande | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonDeCommande }) => (this.bonDeCommande = bonDeCommande));
  }

  previousState(): void {
    window.history.back();
  }
}
