import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatRdvDetailComponent } from 'app/entities/etat-rdv/etat-rdv-detail.component';
import { EtatRdv } from 'app/shared/model/etat-rdv.model';

describe('Component Tests', () => {
  describe('EtatRdv Management Detail Component', () => {
    let comp: EtatRdvDetailComponent;
    let fixture: ComponentFixture<EtatRdvDetailComponent>;
    const route = ({ data: of({ etatRdv: new EtatRdv(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatRdvDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtatRdvDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatRdvDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etatRdv on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatRdv).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
