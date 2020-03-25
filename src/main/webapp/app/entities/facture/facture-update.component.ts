import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacture, Facture } from 'app/shared/model/facture.model';
import { FactureService } from './facture.service';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from 'app/entities/bon-livraison/bon-livraison.service';

@Component({
  selector: 'jhi-facture-update',
  templateUrl: './facture-update.component.html'
})
export class FactureUpdateComponent implements OnInit {
  isSaving = false;
  bonlivraisons: IBonLivraison[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    designation: [],
    montantFact: [],
    montantPaye: [],
    montantGlobal: [],
    moratoire: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    bonLivraison: []
  });

  constructor(
    protected factureService: FactureService,
    protected bonLivraisonService: BonLivraisonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facture }) => {
      this.updateForm(facture);

      this.bonLivraisonService.query().subscribe((res: HttpResponse<IBonLivraison[]>) => (this.bonlivraisons = res.body || []));
    });
  }

  updateForm(facture: IFacture): void {
    this.editForm.patchValue({
      id: facture.id,
      designation: facture.designation,
      montantFact: facture.montantFact,
      montantPaye: facture.montantPaye,
      montantGlobal: facture.montantGlobal,
      moratoire: facture.moratoire,
      dateCreated: facture.dateCreated,
      dateUpdated: facture.dateUpdated,
      dateDeleted: facture.dateDeleted,
      userCreated: facture.userCreated,
      userUpdated: facture.userUpdated,
      userDeleted: facture.userDeleted,
      bonLivraison: facture.bonLivraison
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facture = this.createFromForm();
    if (facture.id !== undefined) {
      this.subscribeToSaveResponse(this.factureService.update(facture));
    } else {
      this.subscribeToSaveResponse(this.factureService.create(facture));
    }
  }

  private createFromForm(): IFacture {
    return {
      ...new Facture(),
      id: this.editForm.get(['id'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      montantFact: this.editForm.get(['montantFact'])!.value,
      montantPaye: this.editForm.get(['montantPaye'])!.value,
      montantGlobal: this.editForm.get(['montantGlobal'])!.value,
      moratoire: this.editForm.get(['moratoire'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      bonLivraison: this.editForm.get(['bonLivraison'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacture>>): void {
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

  trackById(index: number, item: IBonLivraison): any {
    return item.id;
  }
}
