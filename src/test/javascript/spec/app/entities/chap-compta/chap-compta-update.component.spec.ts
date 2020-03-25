import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ChapComptaUpdateComponent } from 'app/entities/chap-compta/chap-compta-update.component';
import { ChapComptaService } from 'app/entities/chap-compta/chap-compta.service';
import { ChapCompta } from 'app/shared/model/chap-compta.model';

describe('Component Tests', () => {
  describe('ChapCompta Management Update Component', () => {
    let comp: ChapComptaUpdateComponent;
    let fixture: ComponentFixture<ChapComptaUpdateComponent>;
    let service: ChapComptaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ChapComptaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ChapComptaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChapComptaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChapComptaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ChapCompta(123);
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
        const entity = new ChapCompta();
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
