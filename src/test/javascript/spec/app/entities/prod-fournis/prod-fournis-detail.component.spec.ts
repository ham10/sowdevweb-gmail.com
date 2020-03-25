import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ProdFournisDetailComponent } from 'app/entities/prod-fournis/prod-fournis-detail.component';
import { ProdFournis } from 'app/shared/model/prod-fournis.model';

describe('Component Tests', () => {
  describe('ProdFournis Management Detail Component', () => {
    let comp: ProdFournisDetailComponent;
    let fixture: ComponentFixture<ProdFournisDetailComponent>;
    const route = ({ data: of({ prodFournis: new ProdFournis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ProdFournisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProdFournisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProdFournisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load prodFournis on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prodFournis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
