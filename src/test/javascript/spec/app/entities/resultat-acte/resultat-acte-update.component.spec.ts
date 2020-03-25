import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ResultatActeUpdateComponent } from 'app/entities/resultat-acte/resultat-acte-update.component';
import { ResultatActeService } from 'app/entities/resultat-acte/resultat-acte.service';
import { ResultatActe } from 'app/shared/model/resultat-acte.model';

describe('Component Tests', () => {
  describe('ResultatActe Management Update Component', () => {
    let comp: ResultatActeUpdateComponent;
    let fixture: ComponentFixture<ResultatActeUpdateComponent>;
    let service: ResultatActeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ResultatActeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResultatActeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultatActeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultatActeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultatActe(123);
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
        const entity = new ResultatActe();
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
