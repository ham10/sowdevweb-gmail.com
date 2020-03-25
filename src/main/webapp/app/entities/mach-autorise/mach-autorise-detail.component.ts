import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMachAutorise } from 'app/shared/model/mach-autorise.model';

@Component({
  selector: 'jhi-mach-autorise-detail',
  templateUrl: './mach-autorise-detail.component.html'
})
export class MachAutoriseDetailComponent implements OnInit {
  machAutorise: IMachAutorise | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ machAutorise }) => (this.machAutorise = machAutorise));
  }

  previousState(): void {
    window.history.back();
  }
}
