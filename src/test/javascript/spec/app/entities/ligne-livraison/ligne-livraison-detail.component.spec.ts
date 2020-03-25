import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { LigneLivraisonDetailComponent } from 'app/entities/ligne-livraison/ligne-livraison-detail.component';
import { LigneLivraison } from 'app/shared/model/ligne-livraison.model';

describe('Component Tests', () => {
  describe('LigneLivraison Management Detail Component', () => {
    let comp: LigneLivraisonDetailComponent;
    let fixture: ComponentFixture<LigneLivraisonDetailComponent>;
    const route = ({ data: of({ ligneLivraison: new LigneLivraison(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [LigneLivraisonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LigneLivraisonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LigneLivraisonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ligneLivraison on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ligneLivraison).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
