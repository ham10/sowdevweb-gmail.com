import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePlanningDetailComponent } from 'app/entities/type-planning/type-planning-detail.component';
import { TypePlanning } from 'app/shared/model/type-planning.model';

describe('Component Tests', () => {
  describe('TypePlanning Management Detail Component', () => {
    let comp: TypePlanningDetailComponent;
    let fixture: ComponentFixture<TypePlanningDetailComponent>;
    const route = ({ data: of({ typePlanning: new TypePlanning(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePlanningDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypePlanningDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypePlanningDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typePlanning on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typePlanning).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
