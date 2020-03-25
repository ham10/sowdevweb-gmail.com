import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EcheancierDetailComponent } from 'app/entities/echeancier/echeancier-detail.component';
import { Echeancier } from 'app/shared/model/echeancier.model';

describe('Component Tests', () => {
  describe('Echeancier Management Detail Component', () => {
    let comp: EcheancierDetailComponent;
    let fixture: ComponentFixture<EcheancierDetailComponent>;
    const route = ({ data: of({ echeancier: new Echeancier(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EcheancierDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EcheancierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EcheancierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load echeancier on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.echeancier).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
