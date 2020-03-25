import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBoxe, Boxe } from 'app/shared/model/boxe.model';
import { BoxeService } from './boxe.service';
import { IChambre } from 'app/shared/model/chambre.model';
import { ChambreService } from 'app/entities/chambre/chambre.service';

@Component({
  selector: 'jhi-boxe-update',
  templateUrl: './boxe-update.component.html'
})
export class BoxeUpdateComponent implements OnInit {
  isSaving = false;
  chambres: IChambre[] = [];
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    numeroBoxe: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    chambre: []
  });

  constructor(
    protected boxeService: BoxeService,
    protected chambreService: ChambreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boxe }) => {
      this.updateForm(boxe);

      this.chambreService.query().subscribe((res: HttpResponse<IChambre[]>) => (this.chambres = res.body || []));
    });
  }

  updateForm(boxe: IBoxe): void {
    this.editForm.patchValue({
      id: boxe.id,
      numeroBoxe: boxe.numeroBoxe,
      dateCreated: boxe.dateCreated,
      dateUpdated: boxe.dateUpdated,
      userCreated: boxe.userCreated,
      userUpdated: boxe.userUpdated,
      userDeleted: boxe.userDeleted,
      chambre: boxe.chambre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const boxe = this.createFromForm();
    if (boxe.id !== undefined) {
      this.subscribeToSaveResponse(this.boxeService.update(boxe));
    } else {
      this.subscribeToSaveResponse(this.boxeService.create(boxe));
    }
  }

  private createFromForm(): IBoxe {
    return {
      ...new Boxe(),
      id: this.editForm.get(['id'])!.value,
      numeroBoxe: this.editForm.get(['numeroBoxe'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      chambre: this.editForm.get(['chambre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoxe>>): void {
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

  trackById(index: number, item: IChambre): any {
    return item.id;
  }
}
