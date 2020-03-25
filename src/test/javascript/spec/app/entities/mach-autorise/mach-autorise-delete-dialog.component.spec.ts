import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HpdTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { MachAutoriseDeleteDialogComponent } from 'app/entities/mach-autorise/mach-autorise-delete-dialog.component';
import { MachAutoriseService } from 'app/entities/mach-autorise/mach-autorise.service';

describe('Component Tests', () => {
  describe('MachAutorise Management Delete Component', () => {
    let comp: MachAutoriseDeleteDialogComponent;
    let fixture: ComponentFixture<MachAutoriseDeleteDialogComponent>;
    let service: MachAutoriseService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [MachAutoriseDeleteDialogComponent]
      })
        .overrideTemplate(MachAutoriseDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MachAutoriseDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MachAutoriseService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
