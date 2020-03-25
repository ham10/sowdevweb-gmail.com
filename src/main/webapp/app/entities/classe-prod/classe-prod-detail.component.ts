import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClasseProd } from 'app/shared/model/classe-prod.model';

@Component({
  selector: 'jhi-classe-prod-detail',
  templateUrl: './classe-prod-detail.component.html'
})
export class ClasseProdDetailComponent implements OnInit {
  classeProd: IClasseProd | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classeProd }) => (this.classeProd = classeProd));
  }

  previousState(): void {
    window.history.back();
  }
}
