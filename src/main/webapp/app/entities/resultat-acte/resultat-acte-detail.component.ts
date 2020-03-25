import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultatActe } from 'app/shared/model/resultat-acte.model';

@Component({
  selector: 'jhi-resultat-acte-detail',
  templateUrl: './resultat-acte-detail.component.html'
})
export class ResultatActeDetailComponent implements OnInit {
  resultatActe: IResultatActe | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultatActe }) => (this.resultatActe = resultatActe));
  }

  previousState(): void {
    window.history.back();
  }
}
