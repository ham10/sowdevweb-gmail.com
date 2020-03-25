import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ActeMedicalDetailComponent } from 'app/entities/acte-medical/acte-medical-detail.component';
import { ActeMedical } from 'app/shared/model/acte-medical.model';

describe('Component Tests', () => {
  describe('ActeMedical Management Detail Component', () => {
    let comp: ActeMedicalDetailComponent;
    let fixture: ComponentFixture<ActeMedicalDetailComponent>;
    const route = ({ data: of({ acteMedical: new ActeMedical(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ActeMedicalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActeMedicalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActeMedicalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load acteMedical on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.acteMedical).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
