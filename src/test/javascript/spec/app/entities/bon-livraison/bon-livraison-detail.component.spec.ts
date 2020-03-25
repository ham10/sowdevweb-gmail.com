import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { BonLivraisonDetailComponent } from 'app/entities/bon-livraison/bon-livraison-detail.component';
import { BonLivraison } from 'app/shared/model/bon-livraison.model';

describe('Component Tests', () => {
  describe('BonLivraison Management Detail Component', () => {
    let comp: BonLivraisonDetailComponent;
    let fixture: ComponentFixture<BonLivraisonDetailComponent>;
    const route = ({ data: of({ bonLivraison: new BonLivraison(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [BonLivraisonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BonLivraisonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BonLivraisonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bonLivraison on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bonLivraison).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
