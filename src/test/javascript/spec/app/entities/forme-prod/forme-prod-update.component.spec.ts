import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { FormeProdUpdateComponent } from 'app/entities/forme-prod/forme-prod-update.component';
import { FormeProdService } from 'app/entities/forme-prod/forme-prod.service';
import { FormeProd } from 'app/shared/model/forme-prod.model';

describe('Component Tests', () => {
  describe('FormeProd Management Update Component', () => {
    let comp: FormeProdUpdateComponent;
    let fixture: ComponentFixture<FormeProdUpdateComponent>;
    let service: FormeProdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [FormeProdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FormeProdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FormeProdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormeProdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FormeProd(123);
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
        const entity = new FormeProd();
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
