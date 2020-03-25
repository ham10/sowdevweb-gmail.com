import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { RDVUpdateComponent } from 'app/entities/rdv/rdv-update.component';
import { RDVService } from 'app/entities/rdv/rdv.service';
import { RDV } from 'app/shared/model/rdv.model';

describe('Component Tests', () => {
  describe('RDV Management Update Component', () => {
    let comp: RDVUpdateComponent;
    let fixture: ComponentFixture<RDVUpdateComponent>;
    let service: RDVService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [RDVUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RDVUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RDVUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RDVService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RDV(123);
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
        const entity = new RDV();
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
