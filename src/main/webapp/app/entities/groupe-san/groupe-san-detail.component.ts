import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupeSan } from 'app/shared/model/groupe-san.model';

@Component({
  selector: 'jhi-groupe-san-detail',
  templateUrl: './groupe-san-detail.component.html'
})
export class GroupeSanDetailComponent implements OnInit {
  groupeSan: IGroupeSan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupeSan }) => (this.groupeSan = groupeSan));
  }

  previousState(): void {
    window.history.back();
  }
}
