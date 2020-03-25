import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CatMagasinUpdateComponent } from 'app/entities/cat-magasin/cat-magasin-update.component';
import { CatMagasinService } from 'app/entities/cat-magasin/cat-magasin.service';
import { CatMagasin } from 'app/shared/model/cat-magasin.model';

describe('Component Tests', () => {
  describe('CatMagasin Management Update Component', () => {
    let comp: CatMagasinUpdateComponent;
    let fixture: ComponentFixture<CatMagasinUpdateComponent>;
    let service: CatMagasinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CatMagasinUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CatMagasinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatMagasinUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatMagasinService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatMagasin(123);
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
        const entity = new CatMagasin();
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
