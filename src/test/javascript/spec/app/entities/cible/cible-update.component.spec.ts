import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CibleUpdateComponent } from 'app/entities/cible/cible-update.component';
import { CibleService } from 'app/entities/cible/cible.service';
import { Cible } from 'app/shared/model/cible.model';

describe('Component Tests', () => {
  describe('Cible Management Update Component', () => {
    let comp: CibleUpdateComponent;
    let fixture: ComponentFixture<CibleUpdateComponent>;
    let service: CibleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CibleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CibleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CibleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CibleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cible(123);
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
        const entity = new Cible();
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
