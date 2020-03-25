import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtagereUpdateComponent } from 'app/entities/etagere/etagere-update.component';
import { EtagereService } from 'app/entities/etagere/etagere.service';
import { Etagere } from 'app/shared/model/etagere.model';

describe('Component Tests', () => {
  describe('Etagere Management Update Component', () => {
    let comp: EtagereUpdateComponent;
    let fixture: ComponentFixture<EtagereUpdateComponent>;
    let service: EtagereService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtagereUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtagereUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtagereUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtagereService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Etagere(123);
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
        const entity = new Etagere();
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
