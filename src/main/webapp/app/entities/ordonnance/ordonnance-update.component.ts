import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrdonnance, Ordonnance } from 'app/shared/model/ordonnance.model';
import { OrdonnanceService } from './ordonnance.service';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';
import { FicheMedicalService } from 'app/entities/fiche-medical/fiche-medical.service';

@Component({
  selector: 'jhi-ordonnance-update',
  templateUrl: './ordonnance-update.component.html'
})
export class OrdonnanceUpdateComponent implements OnInit {
  isSaving = false;
  fichemedicals: IFicheMedical[] = [];
  dateDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [],
    date: [],
    description: [],
    prescription: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    ficheMedical: []
  });

  constructor(
    protected ordonnanceService: OrdonnanceService,
    protected ficheMedicalService: FicheMedicalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ordonnance }) => {
      this.updateForm(ordonnance);

      this.ficheMedicalService.query().subscribe((res: HttpResponse<IFicheMedical[]>) => (this.fichemedicals = res.body || []));
    });
  }

  updateForm(ordonnance: IOrdonnance): void {
    this.editForm.patchValue({
      id: ordonnance.id,
      numero: ordonnance.numero,
      date: ordonnance.date,
      description: ordonnance.description,
      prescription: ordonnance.prescription,
      dateCreated: ordonnance.dateCreated,
      dateUpdated: ordonnance.dateUpdated,
      userCreated: ordonnance.userCreated,
      userUpdated: ordonnance.userUpdated,
      userDeleted: ordonnance.userDeleted,
      ficheMedical: ordonnance.ficheMedical
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ordonnance = this.createFromForm();
    if (ordonnance.id !== undefined) {
      this.subscribeToSaveResponse(this.ordonnanceService.update(ordonnance));
    } else {
      this.subscribeToSaveResponse(this.ordonnanceService.create(ordonnance));
    }
  }

  private createFromForm(): IOrdonnance {
    return {
      ...new Ordonnance(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      date: this.editForm.get(['date'])!.value,
      description: this.editForm.get(['description'])!.value,
      prescription: this.editForm.get(['prescription'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      ficheMedical: this.editForm.get(['ficheMedical'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrdonnance>>): void {
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

  trackById(index: number, item: IFicheMedical): any {
    return item.id;
  }
}
