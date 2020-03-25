import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChambre, Chambre } from 'app/shared/model/chambre.model';
import { ChambreService } from './chambre.service';
import { ICatChambre } from 'app/shared/model/cat-chambre.model';
import { CatChambreService } from 'app/entities/cat-chambre/cat-chambre.service';

@Component({
  selector: 'jhi-chambre-update',
  templateUrl: './chambre-update.component.html'
})
export class ChambreUpdateComponent implements OnInit {
  isSaving = false;
  catchambres: ICatChambre[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    numeroChambre: [],
    postTelChambre: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    categorieChambre: []
  });

  constructor(
    protected chambreService: ChambreService,
    protected catChambreService: CatChambreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chambre }) => {
      this.updateForm(chambre);

      this.catChambreService.query().subscribe((res: HttpResponse<ICatChambre[]>) => (this.catchambres = res.body || []));
    });
  }

  updateForm(chambre: IChambre): void {
    this.editForm.patchValue({
      id: chambre.id,
      numeroChambre: chambre.numeroChambre,
      postTelChambre: chambre.postTelChambre,
      dateCreated: chambre.dateCreated,
      dateUpdated: chambre.dateUpdated,
      userCreated: chambre.userCreated,
      userUpdated: chambre.userUpdated,
      userDeleted: chambre.userDeleted,
      categorieChambre: chambre.categorieChambre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chambre = this.createFromForm();
    if (chambre.id !== undefined) {
      this.subscribeToSaveResponse(this.chambreService.update(chambre));
    } else {
      this.subscribeToSaveResponse(this.chambreService.create(chambre));
    }
  }

  private createFromForm(): IChambre {
    return {
      ...new Chambre(),
      id: this.editForm.get(['id'])!.value,
      numeroChambre: this.editForm.get(['numeroChambre'])!.value,
      postTelChambre: this.editForm.get(['postTelChambre'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      categorieChambre: this.editForm.get(['categorieChambre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChambre>>): void {
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

  trackById(index: number, item: ICatChambre): any {
    return item.id;
  }
}
