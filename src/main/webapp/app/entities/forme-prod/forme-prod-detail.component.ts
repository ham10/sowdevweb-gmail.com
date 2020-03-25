import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormeProd } from 'app/shared/model/forme-prod.model';

@Component({
  selector: 'jhi-forme-prod-detail',
  templateUrl: './forme-prod-detail.component.html'
})
export class FormeProdDetailComponent implements OnInit {
  formeProd: IFormeProd | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formeProd }) => (this.formeProd = formeProd));
  }

  previousState(): void {
    window.history.back();
  }
}
