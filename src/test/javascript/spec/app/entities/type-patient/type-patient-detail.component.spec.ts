import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePatientDetailComponent } from 'app/entities/type-patient/type-patient-detail.component';
import { TypePatient } from 'app/shared/model/type-patient.model';

describe('Component Tests', () => {
  describe('TypePatient Management Detail Component', () => {
    let comp: TypePatientDetailComponent;
    let fixture: ComponentFixture<TypePatientDetailComponent>;
    const route = ({ data: of({ typePatient: new TypePatient(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePatientDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypePatientDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypePatientDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typePatient on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typePatient).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
