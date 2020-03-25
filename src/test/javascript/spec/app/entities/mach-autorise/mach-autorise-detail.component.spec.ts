import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { MachAutoriseDetailComponent } from 'app/entities/mach-autorise/mach-autorise-detail.component';
import { MachAutorise } from 'app/shared/model/mach-autorise.model';

describe('Component Tests', () => {
  describe('MachAutorise Management Detail Component', () => {
    let comp: MachAutoriseDetailComponent;
    let fixture: ComponentFixture<MachAutoriseDetailComponent>;
    const route = ({ data: of({ machAutorise: new MachAutorise(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [MachAutoriseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MachAutoriseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MachAutoriseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load machAutorise on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.machAutorise).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
