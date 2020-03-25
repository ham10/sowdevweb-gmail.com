import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HpdTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { JourFerieDeleteDialogComponent } from 'app/entities/jour-ferie/jour-ferie-delete-dialog.component';
import { JourFerieService } from 'app/entities/jour-ferie/jour-ferie.service';

describe('Component Tests', () => {
  describe('JourFerie Management Delete Component', () => {
    let comp: JourFerieDeleteDialogComponent;
    let fixture: ComponentFixture<JourFerieDeleteDialogComponent>;
    let service: JourFerieService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [JourFerieDeleteDialogComponent]
      })
        .overrideTemplate(JourFerieDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JourFerieDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourFerieService);
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
