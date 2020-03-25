import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeFournisUpdateComponent } from 'app/entities/type-fournis/type-fournis-update.component';
import { TypeFournisService } from 'app/entities/type-fournis/type-fournis.service';
import { TypeFournis } from 'app/shared/model/type-fournis.model';

describe('Component Tests', () => {
  describe('TypeFournis Management Update Component', () => {
    let comp: TypeFournisUpdateComponent;
    let fixture: ComponentFixture<TypeFournisUpdateComponent>;
    let service: TypeFournisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeFournisUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeFournisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeFournisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeFournisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeFournis(123);
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
        const entity = new TypeFournis();
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
