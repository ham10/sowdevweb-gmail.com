import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITarif, Tarif } from 'app/shared/model/tarif.model';
import { TarifService } from './tarif.service';
import { ITypeSoins } from 'app/shared/model/type-soins.model';
import { TypeSoinsService } from 'app/entities/type-soins/type-soins.service';
import { ISousFamille } from 'app/shared/model/sous-famille.model';
import { SousFamilleService } from 'app/entities/sous-famille/sous-famille.service';
import { IActeMedical } from 'app/shared/model/acte-medical.model';
import { ActeMedicalService } from 'app/entities/acte-medical/acte-medical.service';
import { ICatChambre } from 'app/shared/model/cat-chambre.model';
import { CatChambreService } from 'app/entities/cat-chambre/cat-chambre.service';

type SelectableEntity = ITypeSoins | ISousFamille | IActeMedical | ICatChambre;

@Component({
  selector: 'jhi-tarif-update',
  templateUrl: './tarif-update.component.html'
})
export class TarifUpdateComponent implements OnInit {
  isSaving = false;
  typesoins: ITypeSoins[] = [];
  sousfamilles: ISousFamille[] = [];
  actemedicals: IActeMedical[] = [];
  catchambres: ICatChambre[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;
  dateDeletedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    montant: [],
    pourcentage: [],
    dateCreated: [],
    dateUpdated: [],
    dateDeleted: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typeSoins: [],
    sousfamille: [],
    actemedical: [],
    categorieChambre: []
  });

  constructor(
    protected tarifService: TarifService,
    protected typeSoinsService: TypeSoinsService,
    protected sousFamilleService: SousFamilleService,
    protected acteMedicalService: ActeMedicalService,
    protected catChambreService: CatChambreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tarif }) => {
      this.updateForm(tarif);

      this.typeSoinsService.query().subscribe((res: HttpResponse<ITypeSoins[]>) => (this.typesoins = res.body || []));

      this.sousFamilleService.query().subscribe((res: HttpResponse<ISousFamille[]>) => (this.sousfamilles = res.body || []));

      this.acteMedicalService.query().subscribe((res: HttpResponse<IActeMedical[]>) => (this.actemedicals = res.body || []));

      this.catChambreService.query().subscribe((res: HttpResponse<ICatChambre[]>) => (this.catchambres = res.body || []));
    });
  }

  updateForm(tarif: ITarif): void {
    this.editForm.patchValue({
      id: tarif.id,
      code: tarif.code,
      libelle: tarif.libelle,
      montant: tarif.montant,
      pourcentage: tarif.pourcentage,
      dateCreated: tarif.dateCreated,
      dateUpdated: tarif.dateUpdated,
      dateDeleted: tarif.dateDeleted,
      userCreated: tarif.userCreated,
      userUpdated: tarif.userUpdated,
      userDeleted: tarif.userDeleted,
      typeSoins: tarif.typeSoins,
      sousfamille: tarif.sousfamille,
      actemedical: tarif.actemedical,
      categorieChambre: tarif.categorieChambre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tarif = this.createFromForm();
    if (tarif.id !== undefined) {
      this.subscribeToSaveResponse(this.tarifService.update(tarif));
    } else {
      this.subscribeToSaveResponse(this.tarifService.create(tarif));
    }
  }

  private createFromForm(): ITarif {
    return {
      ...new Tarif(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      pourcentage: this.editForm.get(['pourcentage'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      dateDeleted: this.editForm.get(['dateDeleted'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typeSoins: this.editForm.get(['typeSoins'])!.value,
      sousfamille: this.editForm.get(['sousfamille'])!.value,
      actemedical: this.editForm.get(['actemedical'])!.value,
      categorieChambre: this.editForm.get(['categorieChambre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarif>>): void {
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
}
