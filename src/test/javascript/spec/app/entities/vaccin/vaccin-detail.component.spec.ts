import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { VaccinDetailComponent } from 'app/entities/vaccin/vaccin-detail.component';
import { Vaccin } from 'app/shared/model/vaccin.model';

describe('Component Tests', () => {
  describe('Vaccin Management Detail Component', () => {
    let comp: VaccinDetailComponent;
    let fixture: ComponentFixture<VaccinDetailComponent>;
    const route = ({ data: of({ vaccin: new Vaccin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [VaccinDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VaccinDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VaccinDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vaccin on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vaccin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
