import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeQuestionUpdateComponent } from 'app/entities/type-question/type-question-update.component';
import { TypeQuestionService } from 'app/entities/type-question/type-question.service';
import { TypeQuestion } from 'app/shared/model/type-question.model';

describe('Component Tests', () => {
  describe('TypeQuestion Management Update Component', () => {
    let comp: TypeQuestionUpdateComponent;
    let fixture: ComponentFixture<TypeQuestionUpdateComponent>;
    let service: TypeQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeQuestionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeQuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeQuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeQuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeQuestion(123);
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
        const entity = new TypeQuestion();
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
