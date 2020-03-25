import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompteGene } from 'app/shared/model/compte-gene.model';
import { CompteGeneService } from './compte-gene.service';

@Component({
  templateUrl: './compte-gene-delete-dialog.component.html'
})
export class CompteGeneDeleteDialogComponent {
  compteGene?: ICompteGene;

  constructor(
    protected compteGeneService: CompteGeneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.compteGeneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('compteGeneListModification');
      this.activeModal.close();
    });
  }
}
