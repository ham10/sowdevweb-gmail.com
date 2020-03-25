import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFicheMedical, FicheMedical } from 'app/shared/model/fiche-medical.model';
import { FicheMedicalService } from './fiche-medical.service';
import { IMedecin } from 'app/shared/model/medecin.model';
import { MedecinService } from 'app/entities/medecin/medecin.service';
import { ITypeConstante } from 'app/shared/model/type-constante.model';
import { TypeConstanteService } from 'app/entities/type-constante/type-constante.service';

type SelectableEntity = IMedecin | ITypeConstante;

@Component({
  selector: 'jhi-fiche-medical-update',
  templateUrl: './fiche-medical-update.component.html'
})
export class FicheMedicalUpdateComponent implements OnInit {
  isSaving = false;
  medecins: IMedecin[] = [];
  typeconstantes: ITypeConstante[] = [];
  dateConsultationDp: any;
  dateProchainRVDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    numeroFicheMedical: [],
    dateConsultation: [],
    facteurRisque: [],
    regimeAlimentaire: [],
    diagnostic: [],
    recommandations: [],
    commentaires: [],
    dateProchainRV: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    medecin: [],
    typeConstantes: []
  });

  constructor(
    protected ficheMedicalService: FicheMedicalService,
    protected medecinService: MedecinService,
    protected typeConstanteService: TypeConstanteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ficheMedical }) => {
      this.updateForm(ficheMedical);

      this.medecinService.query().subscribe((res: HttpResponse<IMedecin[]>) => (this.medecins = res.body || []));

      this.typeConstanteService.query().subscribe((res: HttpResponse<ITypeConstante[]>) => (this.typeconstantes = res.body || []));
    });
  }

  updateForm(ficheMedical: IFicheMedical): void {
    this.editForm.patchValue({
      id: ficheMedical.id,
      numeroFicheMedical: ficheMedical.numeroFicheMedical,
      dateConsultation: ficheMedical.dateConsultation,
      facteurRisque: ficheMedical.facteurRisque,
      regimeAlimentaire: ficheMedical.regimeAlimentaire,
      diagnostic: ficheMedical.diagnostic,
      recommandations: ficheMedical.recommandations,
      commentaires: ficheMedical.commentaires,
      dateProchainRV: ficheMedical.dateProchainRV,
      dateCreated: ficheMedical.dateCreated,
      dateUpdated: ficheMedical.dateUpdated,
      userCreated: ficheMedical.userCreated,
      userUpdated: ficheMedical.userUpdated,
      userDeleted: ficheMedical.userDeleted,
      medecin: ficheMedical.medecin,
      typeConstantes: ficheMedical.typeConstantes
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ficheMedical = this.createFromForm();
    if (ficheMedical.id !== undefined) {
      this.subscribeToSaveResponse(this.ficheMedicalService.update(ficheMedical));
    } else {
      this.subscribeToSaveResponse(this.ficheMedicalService.create(ficheMedical));
    }
  }

  private createFromForm(): IFicheMedical {
    return {
      ...new FicheMedical(),
      id: this.editForm.get(['id'])!.value,
      numeroFicheMedical: this.editForm.get(['numeroFicheMedical'])!.value,
      dateConsultation: this.editForm.get(['dateConsultation'])!.value,
      facteurRisque: this.editForm.get(['facteurRisque'])!.value,
      regimeAlimentaire: this.editForm.get(['regimeAlimentaire'])!.value,
      diagnostic: this.editForm.get(['diagnostic'])!.value,
      recommandations: this.editForm.get(['recommandations'])!.value,
      commentaires: this.editForm.get(['commentaires'])!.value,
      dateProchainRV: this.editForm.get(['dateProchainRV'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      medecin: this.editForm.get(['medecin'])!.value,
      typeConstantes: this.editForm.get(['typeConstantes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFicheMedical>>): void {
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

  getSelected(selectedVals: ITypeConstante[], option: ITypeConstante): ITypeConstante {
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
