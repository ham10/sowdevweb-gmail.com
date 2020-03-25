import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchemaCompta } from 'app/shared/model/schema-compta.model';

@Component({
  selector: 'jhi-schema-compta-detail',
  templateUrl: './schema-compta-detail.component.html'
})
export class SchemaComptaDetailComponent implements OnInit {
  schemaCompta: ISchemaCompta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schemaCompta }) => (this.schemaCompta = schemaCompta));
  }

  previousState(): void {
    window.history.back();
  }
}
