import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { RDVDetailComponent } from 'app/entities/rdv/rdv-detail.component';
import { RDV } from 'app/shared/model/rdv.model';

describe('Component Tests', () => {
  describe('RDV Management Detail Component', () => {
    let comp: RDVDetailComponent;
    let fixture: ComponentFixture<RDVDetailComponent>;
    const route = ({ data: of({ rDV: new RDV(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [RDVDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RDVDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RDVDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rDV on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rDV).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
