import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { RayonDetailComponent } from 'app/entities/rayon/rayon-detail.component';
import { Rayon } from 'app/shared/model/rayon.model';

describe('Component Tests', () => {
  describe('Rayon Management Detail Component', () => {
    let comp: RayonDetailComponent;
    let fixture: ComponentFixture<RayonDetailComponent>;
    const route = ({ data: of({ rayon: new Rayon(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [RayonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RayonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RayonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rayon on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rayon).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
