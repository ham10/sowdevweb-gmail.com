import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { HoraireConDetailComponent } from 'app/entities/horaire-con/horaire-con-detail.component';
import { HoraireCon } from 'app/shared/model/horaire-con.model';

describe('Component Tests', () => {
  describe('HoraireCon Management Detail Component', () => {
    let comp: HoraireConDetailComponent;
    let fixture: ComponentFixture<HoraireConDetailComponent>;
    const route = ({ data: of({ horaireCon: new HoraireCon(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [HoraireConDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HoraireConDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HoraireConDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load horaireCon on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.horaireCon).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
