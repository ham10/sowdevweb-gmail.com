import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeServices } from 'app/shared/model/type-services.model';

@Component({
  selector: 'jhi-type-services-detail',
  templateUrl: './type-services-detail.component.html'
})
export class TypeServicesDetailComponent implements OnInit {
  typeServices: ITypeServices | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeServices }) => (this.typeServices = typeServices));
  }

  previousState(): void {
    window.history.back();
  }
}
