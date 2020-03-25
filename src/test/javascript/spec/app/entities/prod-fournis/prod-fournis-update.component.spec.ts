import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ProdFournisUpdateComponent } from 'app/entities/prod-fournis/prod-fournis-update.component';
import { ProdFournisService } from 'app/entities/prod-fournis/prod-fournis.service';
import { ProdFournis } from 'app/shared/model/prod-fournis.model';

describe('Component Tests', () => {
  describe('ProdFournis Management Update Component', () => {
    let comp: ProdFournisUpdateComponent;
    let fixture: ComponentFixture<ProdFournisUpdateComponent>;
    let service: ProdFournisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ProdFournisUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProdFournisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdFournisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdFournisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProdFournis(123);
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
        const entity = new ProdFournis();
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
