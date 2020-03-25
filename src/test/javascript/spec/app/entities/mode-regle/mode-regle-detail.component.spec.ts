import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ModeRegleDetailComponent } from 'app/entities/mode-regle/mode-regle-detail.component';
import { ModeRegle } from 'app/shared/model/mode-regle.model';

describe('Component Tests', () => {
  describe('ModeRegle Management Detail Component', () => {
    let comp: ModeRegleDetailComponent;
    let fixture: ComponentFixture<ModeRegleDetailComponent>;
    const route = ({ data: of({ modeRegle: new ModeRegle(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ModeRegleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ModeRegleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModeRegleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load modeRegle on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modeRegle).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
