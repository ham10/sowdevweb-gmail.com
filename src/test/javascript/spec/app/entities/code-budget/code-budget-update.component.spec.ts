import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CodeBudgetUpdateComponent } from 'app/entities/code-budget/code-budget-update.component';
import { CodeBudgetService } from 'app/entities/code-budget/code-budget.service';
import { CodeBudget } from 'app/shared/model/code-budget.model';

describe('Component Tests', () => {
  describe('CodeBudget Management Update Component', () => {
    let comp: CodeBudgetUpdateComponent;
    let fixture: ComponentFixture<CodeBudgetUpdateComponent>;
    let service: CodeBudgetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CodeBudgetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CodeBudgetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CodeBudgetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodeBudgetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CodeBudget(123);
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
        const entity = new CodeBudget();
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
