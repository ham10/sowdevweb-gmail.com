import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { SousFamilleDetailComponent } from 'app/entities/sous-famille/sous-famille-detail.component';
import { SousFamille } from 'app/shared/model/sous-famille.model';

describe('Component Tests', () => {
  describe('SousFamille Management Detail Component', () => {
    let comp: SousFamilleDetailComponent;
    let fixture: ComponentFixture<SousFamilleDetailComponent>;
    const route = ({ data: of({ sousFamille: new SousFamille(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [SousFamilleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SousFamilleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SousFamilleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sousFamille on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sousFamille).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
