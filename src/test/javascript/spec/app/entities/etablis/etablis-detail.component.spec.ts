import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtablisDetailComponent } from 'app/entities/etablis/etablis-detail.component';
import { Etablis } from 'app/shared/model/etablis.model';

describe('Component Tests', () => {
  describe('Etablis Management Detail Component', () => {
    let comp: EtablisDetailComponent;
    let fixture: ComponentFixture<EtablisDetailComponent>;
    const route = ({ data: of({ etablis: new Etablis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtablisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtablisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtablisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etablis on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etablis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
