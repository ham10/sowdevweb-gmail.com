import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { AyantDroitUpdateComponent } from 'app/entities/ayant-droit/ayant-droit-update.component';
import { AyantDroitService } from 'app/entities/ayant-droit/ayant-droit.service';
import { AyantDroit } from 'app/shared/model/ayant-droit.model';

describe('Component Tests', () => {
  describe('AyantDroit Management Update Component', () => {
    let comp: AyantDroitUpdateComponent;
    let fixture: ComponentFixture<AyantDroitUpdateComponent>;
    let service: AyantDroitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [AyantDroitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AyantDroitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AyantDroitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AyantDroitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AyantDroit(123);
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
        const entity = new AyantDroit();
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
