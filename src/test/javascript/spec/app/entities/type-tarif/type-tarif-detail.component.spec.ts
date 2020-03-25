import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeTarifDetailComponent } from 'app/entities/type-tarif/type-tarif-detail.component';
import { TypeTarif } from 'app/shared/model/type-tarif.model';

describe('Component Tests', () => {
  describe('TypeTarif Management Detail Component', () => {
    let comp: TypeTarifDetailComponent;
    let fixture: ComponentFixture<TypeTarifDetailComponent>;
    const route = ({ data: of({ typeTarif: new TypeTarif(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeTarifDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeTarifDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeTarifDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeTarif on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeTarif).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
