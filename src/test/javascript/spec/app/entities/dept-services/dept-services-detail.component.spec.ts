import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { DeptServicesDetailComponent } from 'app/entities/dept-services/dept-services-detail.component';
import { DeptServices } from 'app/shared/model/dept-services.model';

describe('Component Tests', () => {
  describe('DeptServices Management Detail Component', () => {
    let comp: DeptServicesDetailComponent;
    let fixture: ComponentFixture<DeptServicesDetailComponent>;
    const route = ({ data: of({ deptServices: new DeptServices(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [DeptServicesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeptServicesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeptServicesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deptServices on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deptServices).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
