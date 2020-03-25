import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TauxDeviseDetailComponent } from 'app/entities/taux-devise/taux-devise-detail.component';
import { TauxDevise } from 'app/shared/model/taux-devise.model';

describe('Component Tests', () => {
  describe('TauxDevise Management Detail Component', () => {
    let comp: TauxDeviseDetailComponent;
    let fixture: ComponentFixture<TauxDeviseDetailComponent>;
    const route = ({ data: of({ tauxDevise: new TauxDevise(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TauxDeviseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TauxDeviseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TauxDeviseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tauxDevise on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tauxDevise).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
