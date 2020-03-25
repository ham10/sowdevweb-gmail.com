import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { SitMatUpdateComponent } from 'app/entities/sit-mat/sit-mat-update.component';
import { SitMatService } from 'app/entities/sit-mat/sit-mat.service';
import { SitMat } from 'app/shared/model/sit-mat.model';

describe('Component Tests', () => {
  describe('SitMat Management Update Component', () => {
    let comp: SitMatUpdateComponent;
    let fixture: ComponentFixture<SitMatUpdateComponent>;
    let service: SitMatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [SitMatUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SitMatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SitMatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SitMatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SitMat(123);
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
        const entity = new SitMat();
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
