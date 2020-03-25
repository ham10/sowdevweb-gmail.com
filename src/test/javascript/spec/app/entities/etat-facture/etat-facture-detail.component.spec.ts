import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatFactureDetailComponent } from 'app/entities/etat-facture/etat-facture-detail.component';
import { EtatFacture } from 'app/shared/model/etat-facture.model';

describe('Component Tests', () => {
  describe('EtatFacture Management Detail Component', () => {
    let comp: EtatFactureDetailComponent;
    let fixture: ComponentFixture<EtatFactureDetailComponent>;
    const route = ({ data: of({ etatFacture: new EtatFacture(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatFactureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtatFactureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatFactureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etatFacture on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatFacture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
