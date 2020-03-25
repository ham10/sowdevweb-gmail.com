import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompteGene, CompteGene } from 'app/shared/model/compte-gene.model';
import { CompteGeneService } from './compte-gene.service';
import { IChapCompta } from 'app/shared/model/chap-compta.model';
import { ChapComptaService } from 'app/entities/chap-compta/chap-compta.service';

@Component({
  selector: 'jhi-compte-gene-update',
  templateUrl: './compte-gene-update.component.html'
})
export class CompteGeneUpdateComponent implements OnInit {
  isSaving = false;
  chapcomptas: IChapCompta[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    numeroCompteGene: [],
    libelleCompteGene: [],
    sensCompteGene: [],
    soldeCompteGene: [],
    cumulMouvDebitCompteGene: [],
    cumulMouvCreditCompteGene: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    chapitreComptable: []
  });

  constructor(
    protected compteGeneService: CompteGeneService,
    protected chapComptaService: ChapComptaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compteGene }) => {
      this.updateForm(compteGene);

      this.chapComptaService.query().subscribe((res: HttpResponse<IChapCompta[]>) => (this.chapcomptas = res.body || []));
    });
  }

  updateForm(compteGene: ICompteGene): void {
    this.editForm.patchValue({
      id: compteGene.id,
      numeroCompteGene: compteGene.numeroCompteGene,
      libelleCompteGene: compteGene.libelleCompteGene,
      sensCompteGene: compteGene.sensCompteGene,
      soldeCompteGene: compteGene.soldeCompteGene,
      cumulMouvDebitCompteGene: compteGene.cumulMouvDebitCompteGene,
      cumulMouvCreditCompteGene: compteGene.cumulMouvCreditCompteGene,
      dateCreated: compteGene.dateCreated,
      dateUpdated: compteGene.dateUpdated,
      dateDeleted: compteGene.dateDeleted,
      userCreated: compteGene.userCreated,
      userUpdated: compteGene.userUpdated,
      userDeleted: compteGene.userDeleted,
      chapitreComptable: compteGene.chapitreComptable
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const compteGene = this.createFromForm();
    if (compteGene.id !== undefined) {
      this.subscribeToSaveResponse(this.compteGeneService.update(compteGene));
    } else {
      this.subscribeToSaveResponse(this.compteGeneService.create(compteGene));
    }
  }

  private createFromForm(): ICompteGene {
    return {
      ...new CompteGene(),
      id: this.editForm.get(['id'])!.value,
      numeroCompteGene: this.editForm.get(['numeroCompteGene'])!.value,
      libelleCompteGene: this.editForm.get(['libelleCompteGene'])!.value,
      sensCompteGene: this.editForm.get(['sensCompteGene'])!.value,
      soldeCompteGene: this.editForm.get(['soldeCompteGene'])!.value,
      cumulMouvDebitCompteGene: this.editForm.get(['cumulMouvDebitCompteGene'])!.value,
      cumulMouvCreditCompteGene: this.editForm.get(['cumulMouvCreditCompteGene'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      chapitreComptable: this.editForm.get(['chapitreComptable'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompteGene>>): void {
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

  trackById(index: number, item: IChapCompta): any {
    return item.id;
  }
}
