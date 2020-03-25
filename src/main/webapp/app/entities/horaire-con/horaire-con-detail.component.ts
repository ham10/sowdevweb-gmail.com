import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHoraireCon } from 'app/shared/model/horaire-con.model';

@Component({
  selector: 'jhi-horaire-con-detail',
  templateUrl: './horaire-con-detail.component.html'
})
export class HoraireConDetailComponent implements OnInit {
  horaireCon: IHoraireCon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ horaireCon }) => (this.horaireCon = horaireCon));
  }

  previousState(): void {
    window.history.back();
  }
}
