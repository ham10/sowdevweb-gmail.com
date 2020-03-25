import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatCaisseDetailComponent } from 'app/entities/etat-caisse/etat-caisse-detail.component';
import { EtatCaisse } from 'app/shared/model/etat-caisse.model';

describe('Component Tests', () => {
  describe('EtatCaisse Management Detail Component', () => {
    let comp: EtatCaisseDetailComponent;
    let fixture: ComponentFixture<EtatCaisseDetailComponent>;
    const route = ({ data: of({ etatCaisse: new EtatCaisse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatCaisseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtatCaisseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatCaisseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etatCaisse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatCaisse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
