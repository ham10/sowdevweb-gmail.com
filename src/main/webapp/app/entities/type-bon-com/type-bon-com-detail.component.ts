import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeBonCom } from 'app/shared/model/type-bon-com.model';

@Component({
  selector: 'jhi-type-bon-com-detail',
  templateUrl: './type-bon-com-detail.component.html'
})
export class TypeBonComDetailComponent implements OnInit {
  typeBonCom: ITypeBonCom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeBonCom }) => (this.typeBonCom = typeBonCom));
  }

  previousState(): void {
    window.history.back();
  }
}
