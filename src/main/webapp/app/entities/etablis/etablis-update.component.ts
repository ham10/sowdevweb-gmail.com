import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEtablis, Etablis } from 'app/shared/model/etablis.model';
import { EtablisService } from './etablis.service';
import { IDepartement } from 'app/shared/model/departement.model';
import { DepartementService } from 'app/entities/departement/departement.service';

@Component({
  selector: 'jhi-etablis-update',
  templateUrl: './etablis-update.component.html'
})
export class EtablisUpdateComponent implements OnInit {
  isSaving = false;
  departements: IDepartement[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeEtabl: [],
    raisonSocialeEtabl: [],
    adresseEtabl: [],
    telephoneEtabl: [],
    nineaEtabl: [],
    rcEtabl: [],
    emailEtabl: [],
    description: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    departement: []
  });

  constructor(
    protected etablisService: EtablisService,
    protected departementService: DepartementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablis }) => {
      this.updateForm(etablis);

      this.departementService.query().subscribe((res: HttpResponse<IDepartement[]>) => (this.departements = res.body || []));
    });
  }

  updateForm(etablis: IEtablis): void {
    this.editForm.patchValue({
      id: etablis.id,
      codeEtabl: etablis.codeEtabl,
      raisonSocialeEtabl: etablis.raisonSocialeEtabl,
      adresseEtabl: etablis.adresseEtabl,
      telephoneEtabl: etablis.telephoneEtabl,
      nineaEtabl: etablis.nineaEtabl,
      rcEtabl: etablis.rcEtabl,
      emailEtabl: etablis.emailEtabl,
      description: etablis.description,
      dateCreated: etablis.dateCreated,
      dateUpdated: etablis.dateUpdated,
      userCreated: etablis.userCreated,
      userUpdated: etablis.userUpdated,
      userDeleted: etablis.userDeleted,
      departement: etablis.departement
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etablis = this.createFromForm();
    if (etablis.id !== undefined) {
      this.subscribeToSaveResponse(this.etablisService.update(etablis));
    } else {
      this.subscribeToSaveResponse(this.etablisService.create(etablis));
    }
  }

  private createFromForm(): IEtablis {
    return {
      ...new Etablis(),
      id: this.editForm.get(['id'])!.value,
      codeEtabl: this.editForm.get(['codeEtabl'])!.value,
      raisonSocialeEtabl: this.editForm.get(['raisonSocialeEtabl'])!.value,
      adresseEtabl: this.editForm.get(['adresseEtabl'])!.value,
      telephoneEtabl: this.editForm.get(['telephoneEtabl'])!.value,
      nineaEtabl: this.editForm.get(['nineaEtabl'])!.value,
      rcEtabl: this.editForm.get(['rcEtabl'])!.value,
      emailEtabl: this.editForm.get(['emailEtabl'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      departement: this.editForm.get(['departement'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtablis>>): void {
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

  trackById(index: number, item: IDepartement): any {
    return item.id;
  }
}
