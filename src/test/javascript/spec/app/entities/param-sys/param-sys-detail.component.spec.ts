import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ParamSysDetailComponent } from 'app/entities/param-sys/param-sys-detail.component';
import { ParamSys } from 'app/shared/model/param-sys.model';

describe('Component Tests', () => {
  describe('ParamSys Management Detail Component', () => {
    let comp: ParamSysDetailComponent;
    let fixture: ComponentFixture<ParamSysDetailComponent>;
    const route = ({ data: of({ paramSys: new ParamSys(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ParamSysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParamSysDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParamSysDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paramSys on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paramSys).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
