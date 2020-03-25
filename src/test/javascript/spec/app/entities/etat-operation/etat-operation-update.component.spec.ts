import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatOperationUpdateComponent } from 'app/entities/etat-operation/etat-operation-update.component';
import { EtatOperationService } from 'app/entities/etat-operation/etat-operation.service';
import { EtatOperation } from 'app/shared/model/etat-operation.model';

describe('Component Tests', () => {
  describe('EtatOperation Management Update Component', () => {
    let comp: EtatOperationUpdateComponent;
    let fixture: ComponentFixture<EtatOperationUpdateComponent>;
    let service: EtatOperationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatOperationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtatOperationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatOperationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatOperationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatOperation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatOperation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
