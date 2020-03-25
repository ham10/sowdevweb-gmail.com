import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ParamCodeUpdateComponent } from 'app/entities/param-code/param-code-update.component';
import { ParamCodeService } from 'app/entities/param-code/param-code.service';
import { ParamCode } from 'app/shared/model/param-code.model';

describe('Component Tests', () => {
  describe('ParamCode Management Update Component', () => {
    let comp: ParamCodeUpdateComponent;
    let fixture: ComponentFixture<ParamCodeUpdateComponent>;
    let service: ParamCodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ParamCodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParamCodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParamCodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParamCodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ParamCode(123);
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
        const entity = new ParamCode();
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
