import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CodeBudgetDetailComponent } from 'app/entities/code-budget/code-budget-detail.component';
import { CodeBudget } from 'app/shared/model/code-budget.model';

describe('Component Tests', () => {
  describe('CodeBudget Management Detail Component', () => {
    let comp: CodeBudgetDetailComponent;
    let fixture: ComponentFixture<CodeBudgetDetailComponent>;
    const route = ({ data: of({ codeBudget: new CodeBudget(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CodeBudgetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CodeBudgetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CodeBudgetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load codeBudget on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.codeBudget).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
