import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { PoleUpdateComponent } from 'app/entities/pole/pole-update.component';
import { PoleService } from 'app/entities/pole/pole.service';
import { Pole } from 'app/shared/model/pole.model';

describe('Component Tests', () => {
  describe('Pole Management Update Component', () => {
    let comp: PoleUpdateComponent;
    let fixture: ComponentFixture<PoleUpdateComponent>;
    let service: PoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [PoleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pole(123);
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
        const entity = new Pole();
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
