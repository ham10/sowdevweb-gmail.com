import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFichier, Fichier } from 'app/shared/model/fichier.model';
import { FichierService } from './fichier.service';

@Component({
  selector: 'jhi-fichier-update',
  templateUrl: './fichier-update.component.html'
})
export class FichierUpdateComponent implements OnInit {
  isSaving = false;
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    cheminFichier: [],
    formatFichier: [],
    separateurFichier: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: []
  });

  constructor(protected fichierService: FichierService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fichier }) => {
      this.updateForm(fichier);
    });
  }

  updateForm(fichier: IFichier): void {
    this.editForm.patchValue({
      id: fichier.id,
      cheminFichier: fichier.cheminFichier,
      formatFichier: fichier.formatFichier,
      separateurFichier: fichier.separateurFichier,
      dateCreated: fichier.dateCreated,
      dateUpdated: fichier.dateUpdated,
      dateDeleted: fichier.dateDeleted,
      userCreated: fichier.userCreated,
      userUpdated: fichier.userUpdated,
      userDeleted: fichier.userDeleted
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fichier = this.createFromForm();
    if (fichier.id !== undefined) {
      this.subscribeToSaveResponse(this.fichierService.update(fichier));
    } else {
      this.subscribeToSaveResponse(this.fichierService.create(fichier));
    }
  }

  private createFromForm(): IFichier {
    return {
      ...new Fichier(),
      id: this.editForm.get(['id'])!.value,
      cheminFichier: this.editForm.get(['cheminFichier'])!.value,
      formatFichier: this.editForm.get(['formatFichier'])!.value,
      separateurFichier: this.editForm.get(['separateurFichier'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichier>>): void {
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
}
