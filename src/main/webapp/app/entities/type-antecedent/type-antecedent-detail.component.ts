import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeAntecedent } from 'app/shared/model/type-antecedent.model';

@Component({
  selector: 'jhi-type-antecedent-detail',
  templateUrl: './type-antecedent-detail.component.html'
})
export class TypeAntecedentDetailComponent implements OnInit {
  typeAntecedent: ITypeAntecedent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAntecedent }) => (this.typeAntecedent = typeAntecedent));
  }

  previousState(): void {
    window.history.back();
  }
}
