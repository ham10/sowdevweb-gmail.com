import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeBonComUpdateComponent } from 'app/entities/type-bon-com/type-bon-com-update.component';
import { TypeBonComService } from 'app/entities/type-bon-com/type-bon-com.service';
import { TypeBonCom } from 'app/shared/model/type-bon-com.model';

describe('Component Tests', () => {
  describe('TypeBonCom Management Update Component', () => {
    let comp: TypeBonComUpdateComponent;
    let fixture: ComponentFixture<TypeBonComUpdateComponent>;
    let service: TypeBonComService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeBonComUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeBonComUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeBonComUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeBonComService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeBonCom(123);
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
        const entity = new TypeBonCom();
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
