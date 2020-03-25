import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IResultatActe, ResultatActe } from 'app/shared/model/resultat-acte.model';
import { ResultatActeService } from './resultat-acte.service';
import { IActeMedical } from 'app/shared/model/acte-medical.model';
import { ActeMedicalService } from 'app/entities/acte-medical/acte-medical.service';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';
import { FicheMedicalService } from 'app/entities/fiche-medical/fiche-medical.service';

type SelectableEntity = IActeMedical | IFicheMedical;

@Component({
  selector: 'jhi-resultat-acte-update',
  templateUrl: './resultat-acte-update.component.html'
})
export class ResultatActeUpdateComponent implements OnInit {
  isSaving = false;
  actemedicals: IActeMedical[] = [];
  fichemedicals: IFicheMedical[] = [];
  dateDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [],
    resultat: [],
    date: [],
    description: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    acteMedicals: [],
    ficheMedical: []
  });

  constructor(
    protected resultatActeService: ResultatActeService,
    protected acteMedicalService: ActeMedicalService,
    protected ficheMedicalService: FicheMedicalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultatActe }) => {
      this.updateForm(resultatActe);

      this.acteMedicalService.query().subscribe((res: HttpResponse<IActeMedical[]>) => (this.actemedicals = res.body || []));

      this.ficheMedicalService.query().subscribe((res: HttpResponse<IFicheMedical[]>) => (this.fichemedicals = res.body || []));
    });
  }

  updateForm(resultatActe: IResultatActe): void {
    this.editForm.patchValue({
      id: resultatActe.id,
      numero: resultatActe.numero,
      resultat: resultatActe.resultat,
      date: resultatActe.date,
      description: resultatActe.description,
      dateCreated: resultatActe.dateCreated,
      dateUpdated: resultatActe.dateUpdated,
      userCreated: resultatActe.userCreated,
      userUpdated: resultatActe.userUpdated,
      userDeleted: resultatActe.userDeleted,
      acteMedicals: resultatActe.acteMedicals,
      ficheMedical: resultatActe.ficheMedical
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultatActe = this.createFromForm();
    if (resultatActe.id !== undefined) {
      this.subscribeToSaveResponse(this.resultatActeService.update(resultatActe));
    } else {
      this.subscribeToSaveResponse(this.resultatActeService.create(resultatActe));
    }
  }

  private createFromForm(): IResultatActe {
    return {
      ...new ResultatActe(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      resultat: this.editForm.get(['resultat'])!.value,
      date: this.editForm.get(['date'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      acteMedicals: this.editForm.get(['acteMedicals'])!.value,
      ficheMedical: this.editForm.get(['ficheMedical'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultatActe>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IActeMedical[], option: IActeMedical): IActeMedical {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
