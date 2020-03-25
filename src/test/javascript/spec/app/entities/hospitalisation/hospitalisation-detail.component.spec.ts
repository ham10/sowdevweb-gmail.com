import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { HospitalisationDetailComponent } from 'app/entities/hospitalisation/hospitalisation-detail.component';
import { Hospitalisation } from 'app/shared/model/hospitalisation.model';

describe('Component Tests', () => {
  describe('Hospitalisation Management Detail Component', () => {
    let comp: HospitalisationDetailComponent;
    let fixture: ComponentFixture<HospitalisationDetailComponent>;
    const route = ({ data: of({ hospitalisation: new Hospitalisation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [HospitalisationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HospitalisationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HospitalisationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hospitalisation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hospitalisation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
