import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatBonComDetailComponent } from 'app/entities/etat-bon-com/etat-bon-com-detail.component';
import { EtatBonCom } from 'app/shared/model/etat-bon-com.model';

describe('Component Tests', () => {
  describe('EtatBonCom Management Detail Component', () => {
    let comp: EtatBonComDetailComponent;
    let fixture: ComponentFixture<EtatBonComDetailComponent>;
    const route = ({ data: of({ etatBonCom: new EtatBonCom(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatBonComDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtatBonComDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatBonComDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etatBonCom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatBonCom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
