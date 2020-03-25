import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatPlanningDetailComponent } from 'app/entities/etat-planning/etat-planning-detail.component';
import { EtatPlanning } from 'app/shared/model/etat-planning.model';

describe('Component Tests', () => {
  describe('EtatPlanning Management Detail Component', () => {
    let comp: EtatPlanningDetailComponent;
    let fixture: ComponentFixture<EtatPlanningDetailComponent>;
    const route = ({ data: of({ etatPlanning: new EtatPlanning(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatPlanningDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtatPlanningDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatPlanningDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etatPlanning on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatPlanning).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
