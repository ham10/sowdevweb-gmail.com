import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IServices, Services } from 'app/shared/model/services.model';
import { ServicesService } from './services.service';
import { ITypeServices } from 'app/shared/model/type-services.model';
import { TypeServicesService } from 'app/entities/type-services/type-services.service';
import { IDeptServices } from 'app/shared/model/dept-services.model';
import { DeptServicesService } from 'app/entities/dept-services/dept-services.service';
import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from 'app/entities/specialite/specialite.service';
import { ICompteGene } from 'app/shared/model/compte-gene.model';
import { CompteGeneService } from 'app/entities/compte-gene/compte-gene.service';

type SelectableEntity = ITypeServices | IDeptServices | ISpecialite | ICompteGene;

@Component({
  selector: 'jhi-services-update',
  templateUrl: './services-update.component.html'
})
export class ServicesUpdateComponent implements OnInit {
  isSaving = false;
  typeservices: ITypeServices[] = [];
  deptservices: IDeptServices[] = [];
  specialites: ISpecialite[] = [];
  comptegenes: ICompteGene[] = [];
  descriptionDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    description: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    typeServices: [],
    deptServices: [],
    specialite: [],
    compteGeneral: []
  });

  constructor(
    protected servicesService: ServicesService,
    protected typeServicesService: TypeServicesService,
    protected deptServicesService: DeptServicesService,
    protected specialiteService: SpecialiteService,
    protected compteGeneService: CompteGeneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ services }) => {
      this.updateForm(services);

      this.typeServicesService.query().subscribe((res: HttpResponse<ITypeServices[]>) => (this.typeservices = res.body || []));

      this.deptServicesService.query().subscribe((res: HttpResponse<IDeptServices[]>) => (this.deptservices = res.body || []));

      this.specialiteService.query().subscribe((res: HttpResponse<ISpecialite[]>) => (this.specialites = res.body || []));

      this.compteGeneService.query().subscribe((res: HttpResponse<ICompteGene[]>) => (this.comptegenes = res.body || []));
    });
  }

  updateForm(services: IServices): void {
    this.editForm.patchValue({
      id: services.id,
      code: services.code,
      libelle: services.libelle,
      description: services.description,
      dateCreated: services.dateCreated,
      dateUpdated: services.dateUpdated,
      userCreated: services.userCreated,
      userUpdated: services.userUpdated,
      userDeleted: services.userDeleted,
      typeServices: services.typeServices,
      deptServices: services.deptServices,
      specialite: services.specialite,
      compteGeneral: services.compteGeneral
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const services = this.createFromForm();
    if (services.id !== undefined) {
      this.subscribeToSaveResponse(this.servicesService.update(services));
    } else {
      this.subscribeToSaveResponse(this.servicesService.create(services));
    }
  }

  private createFromForm(): IServices {
    return {
      ...new Services(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      typeServices: this.editForm.get(['typeServices'])!.value,
      deptServices: this.editForm.get(['deptServices'])!.value,
      specialite: this.editForm.get(['specialite'])!.value,
      compteGeneral: this.editForm.get(['compteGeneral'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServices>>): void {
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
