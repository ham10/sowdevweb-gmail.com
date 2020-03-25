import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ClasseProdUpdateComponent } from 'app/entities/classe-prod/classe-prod-update.component';
import { ClasseProdService } from 'app/entities/classe-prod/classe-prod.service';
import { ClasseProd } from 'app/shared/model/classe-prod.model';

describe('Component Tests', () => {
  describe('ClasseProd Management Update Component', () => {
    let comp: ClasseProdUpdateComponent;
    let fixture: ComponentFixture<ClasseProdUpdateComponent>;
    let service: ClasseProdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ClasseProdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClasseProdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClasseProdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClasseProdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClasseProd(123);
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
        const entity = new ClasseProd();
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
