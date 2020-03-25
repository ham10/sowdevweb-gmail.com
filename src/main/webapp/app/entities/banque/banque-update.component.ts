import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBanque, Banque } from 'app/shared/model/banque.model';
import { BanqueService } from './banque.service';
import { ICompteGene } from 'app/shared/model/compte-gene.model';
import { CompteGeneService } from 'app/entities/compte-gene/compte-gene.service';

@Component({
  selector: 'jhi-banque-update',
  templateUrl: './banque-update.component.html'
})
export class BanqueUpdateComponent implements OnInit {
  isSaving = false;
  comptegenes: ICompteGene[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codeBanque: [],
    ribBanque: [],
    libelleBanque: [],
    descriptionBanque: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    compteGeneral: []
  });

  constructor(
    protected banqueService: BanqueService,
    protected compteGeneService: CompteGeneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ banque }) => {
      this.updateForm(banque);

      this.compteGeneService.query().subscribe((res: HttpResponse<ICompteGene[]>) => (this.comptegenes = res.body || []));
    });
  }

  updateForm(banque: IBanque): void {
    this.editForm.patchValue({
      id: banque.id,
      codeBanque: banque.codeBanque,
      ribBanque: banque.ribBanque,
      libelleBanque: banque.libelleBanque,
      descriptionBanque: banque.descriptionBanque,
      dateCreated: banque.dateCreated,
      dateUpdated: banque.dateUpdated,
      userCreated: banque.userCreated,
      userUpdated: banque.userUpdated,
      userDeleted: banque.userDeleted,
      compteGeneral: banque.compteGeneral
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const banque = this.createFromForm();
    if (banque.id !== undefined) {
      this.subscribeToSaveResponse(this.banqueService.update(banque));
    } else {
      this.subscribeToSaveResponse(this.banqueService.create(banque));
    }
  }

  private createFromForm(): IBanque {
    return {
      ...new Banque(),
      id: this.editForm.get(['id'])!.value,
      codeBanque: this.editForm.get(['codeBanque'])!.value,
      ribBanque: this.editForm.get(['ribBanque'])!.value,
      libelleBanque: this.editForm.get(['libelleBanque'])!.value,
      descriptionBanque: this.editForm.get(['descriptionBanque'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      compteGeneral: this.editForm.get(['compteGeneral'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBanque>>): void {
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

  trackById(index: number, item: ICompteGene): any {
    return item.id;
  }
}
