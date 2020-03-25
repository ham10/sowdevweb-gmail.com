import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeQuestionDetailComponent } from 'app/entities/type-question/type-question-detail.component';
import { TypeQuestion } from 'app/shared/model/type-question.model';

describe('Component Tests', () => {
  describe('TypeQuestion Management Detail Component', () => {
    let comp: TypeQuestionDetailComponent;
    let fixture: ComponentFixture<TypeQuestionDetailComponent>;
    const route = ({ data: of({ typeQuestion: new TypeQuestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeQuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeQuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeQuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeQuestion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeQuestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
