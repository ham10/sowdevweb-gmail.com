import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { JourDetailComponent } from 'app/entities/jour/jour-detail.component';
import { Jour } from 'app/shared/model/jour.model';

describe('Component Tests', () => {
  describe('Jour Management Detail Component', () => {
    let comp: JourDetailComponent;
    let fixture: ComponentFixture<JourDetailComponent>;
    const route = ({ data: of({ jour: new Jour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [JourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jour on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
