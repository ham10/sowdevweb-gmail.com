import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypePiece } from 'app/shared/model/type-piece.model';

@Component({
  selector: 'jhi-type-piece-detail',
  templateUrl: './type-piece-detail.component.html'
})
export class TypePieceDetailComponent implements OnInit {
  typePiece: ITypePiece | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typePiece }) => (this.typePiece = typePiece));
  }

  previousState(): void {
    window.history.back();
  }
}
