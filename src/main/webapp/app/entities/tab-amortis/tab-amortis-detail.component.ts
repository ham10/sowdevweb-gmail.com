import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITabAmortis } from 'app/shared/model/tab-amortis.model';

@Component({
  selector: 'jhi-tab-amortis-detail',
  templateUrl: './tab-amortis-detail.component.html'
})
export class TabAmortisDetailComponent implements OnInit {
  tabAmortis: ITabAmortis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tabAmortis }) => (this.tabAmortis = tabAmortis));
  }

  previousState(): void {
    window.history.back();
  }
}
