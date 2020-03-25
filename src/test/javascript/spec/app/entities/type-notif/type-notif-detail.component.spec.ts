import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeNotifDetailComponent } from 'app/entities/type-notif/type-notif-detail.component';
import { TypeNotif } from 'app/shared/model/type-notif.model';

describe('Component Tests', () => {
  describe('TypeNotif Management Detail Component', () => {
    let comp: TypeNotifDetailComponent;
    let fixture: ComponentFixture<TypeNotifDetailComponent>;
    const route = ({ data: of({ typeNotif: new TypeNotif(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeNotifDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeNotifDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeNotifDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeNotif on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeNotif).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
