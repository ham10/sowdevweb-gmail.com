import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { BonDeCommandeDetailComponent } from 'app/entities/bon-de-commande/bon-de-commande-detail.component';
import { BonDeCommande } from 'app/shared/model/bon-de-commande.model';

describe('Component Tests', () => {
  describe('BonDeCommande Management Detail Component', () => {
    let comp: BonDeCommandeDetailComponent;
    let fixture: ComponentFixture<BonDeCommandeDetailComponent>;
    const route = ({ data: of({ bonDeCommande: new BonDeCommande(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [BonDeCommandeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BonDeCommandeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BonDeCommandeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bonDeCommande on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bonDeCommande).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
