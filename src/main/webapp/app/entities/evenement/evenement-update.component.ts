import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEvenement, Evenement } from 'app/shared/model/evenement.model';
import { EvenementService } from './evenement.service';
import { IActivite } from 'app/shared/model/activite.model';
import { ActiviteService } from 'app/entities/activite/activite.service';

@Component({
  selector: 'jhi-evenement-update',
  templateUrl: './evenement-update.component.html'
})
export class EvenementUpdateComponent implements OnInit {
  isSaving = false;
  activites: IActivite[] = [];

  editForm = this.fb.group({
    id: [],
    heure: [],
    eventName: [],
    description: [],
    dateCreated: [],
    userCreated: [],
    userUpdated: [],
    idEntity: [],
    entityToucher: [],
    activite: []
  });

  constructor(
    protected evenementService: EvenementService,
    protected activiteService: ActiviteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evenement }) => {
      this.updateForm(evenement);

      this.activiteService.query().subscribe((res: HttpResponse<IActivite[]>) => (this.activites = res.body || []));
    });
  }

  updateForm(evenement: IEvenement): void {
    this.editForm.patchValue({
      id: evenement.id,
      heure: evenement.heure,
      eventName: evenement.eventName,
      description: evenement.description,
      dateCreated: evenement.dateCreated,
      userCreated: evenement.userCreated,
      userUpdated: evenement.userUpdated,
      idEntity: evenement.idEntity,
      entityToucher: evenement.entityToucher,
      activite: evenement.activite
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evenement = this.createFromForm();
    if (evenement.id !== undefined) {
      this.subscribeToSaveResponse(this.evenementService.update(evenement));
    } else {
      this.subscribeToSaveResponse(this.evenementService.create(evenement));
    }
  }

  private createFromForm(): IEvenement {
    return {
      ...new Evenement(),
      id: this.editForm.get(['id'])!.value,
      heure: this.editForm.get(['heure'])!.value,
      eventName: this.editForm.get(['eventName'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      idEntity: this.editForm.get(['idEntity'])!.value,
      entityToucher: this.editForm.get(['entityToucher'])!.value,
      activite: this.editForm.get(['activite'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvenement>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IActivite): any {
    return item.id;
  }
}
