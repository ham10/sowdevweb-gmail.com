import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompteGene } from 'app/shared/model/compte-gene.model';

@Component({
  selector: 'jhi-compte-gene-detail',
  templateUrl: './compte-gene-detail.component.html'
})
export class CompteGeneDetailComponent implements OnInit {
  compteGene: ICompteGene | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compteGene }) => (this.compteGene = compteGene));
  }

  previousState(): void {
    window.history.back();
  }
}
