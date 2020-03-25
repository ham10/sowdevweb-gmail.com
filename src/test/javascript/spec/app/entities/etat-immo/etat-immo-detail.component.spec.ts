import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatImmoDetailComponent } from 'app/entities/etat-immo/etat-immo-detail.component';
import { EtatImmo } from 'app/shared/model/etat-immo.model';

describe('Component Tests', () => {
  describe('EtatImmo Management Detail Component', () => {
    let comp: EtatImmoDetailComponent;
    let fixture: ComponentFixture<EtatImmoDetailComponent>;
    const route = ({ data: of({ etatImmo: new EtatImmo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatImmoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtatImmoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatImmoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etatImmo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatImmo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
