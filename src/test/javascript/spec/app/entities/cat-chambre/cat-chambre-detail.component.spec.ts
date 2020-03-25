import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CatChambreDetailComponent } from 'app/entities/cat-chambre/cat-chambre-detail.component';
import { CatChambre } from 'app/shared/model/cat-chambre.model';

describe('Component Tests', () => {
  describe('CatChambre Management Detail Component', () => {
    let comp: CatChambreDetailComponent;
    let fixture: ComponentFixture<CatChambreDetailComponent>;
    const route = ({ data: of({ catChambre: new CatChambre(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CatChambreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CatChambreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatChambreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catChambre on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catChambre).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
