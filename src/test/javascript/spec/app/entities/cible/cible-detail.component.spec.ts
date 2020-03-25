import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CibleDetailComponent } from 'app/entities/cible/cible-detail.component';
import { Cible } from 'app/shared/model/cible.model';

describe('Component Tests', () => {
  describe('Cible Management Detail Component', () => {
    let comp: CibleDetailComponent;
    let fixture: ComponentFixture<CibleDetailComponent>;
    const route = ({ data: of({ cible: new Cible(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CibleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CibleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CibleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cible on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cible).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
