import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { SitMatDetailComponent } from 'app/entities/sit-mat/sit-mat-detail.component';
import { SitMat } from 'app/shared/model/sit-mat.model';

describe('Component Tests', () => {
  describe('SitMat Management Detail Component', () => {
    let comp: SitMatDetailComponent;
    let fixture: ComponentFixture<SitMatDetailComponent>;
    const route = ({ data: of({ sitMat: new SitMat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [SitMatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SitMatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SitMatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sitMat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sitMat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
