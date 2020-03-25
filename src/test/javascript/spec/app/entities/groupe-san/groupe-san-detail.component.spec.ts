import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { GroupeSanDetailComponent } from 'app/entities/groupe-san/groupe-san-detail.component';
import { GroupeSan } from 'app/shared/model/groupe-san.model';

describe('Component Tests', () => {
  describe('GroupeSan Management Detail Component', () => {
    let comp: GroupeSanDetailComponent;
    let fixture: ComponentFixture<GroupeSanDetailComponent>;
    const route = ({ data: of({ groupeSan: new GroupeSan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [GroupeSanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GroupeSanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupeSanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupeSan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupeSan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
