import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { UniteDetailComponent } from 'app/entities/unite/unite-detail.component';
import { Unite } from 'app/shared/model/unite.model';

describe('Component Tests', () => {
  describe('Unite Management Detail Component', () => {
    let comp: UniteDetailComponent;
    let fixture: ComponentFixture<UniteDetailComponent>;
    const route = ({ data: of({ unite: new Unite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [UniteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UniteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UniteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load unite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
