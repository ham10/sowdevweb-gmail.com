import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { BoxeUpdateComponent } from 'app/entities/boxe/boxe-update.component';
import { BoxeService } from 'app/entities/boxe/boxe.service';
import { Boxe } from 'app/shared/model/boxe.model';

describe('Component Tests', () => {
  describe('Boxe Management Update Component', () => {
    let comp: BoxeUpdateComponent;
    let fixture: ComponentFixture<BoxeUpdateComponent>;
    let service: BoxeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [BoxeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BoxeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BoxeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BoxeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Boxe(123);
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
        const entity = new Boxe();
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
