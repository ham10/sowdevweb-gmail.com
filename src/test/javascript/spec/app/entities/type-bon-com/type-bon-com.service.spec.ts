import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TypeBonComService } from 'app/entities/type-bon-com/type-bon-com.service';
import { ITypeBonCom, TypeBonCom } from 'app/shared/model/type-bon-com.model';

describe('Service Tests', () => {
  describe('TypeBonCom Service', () => {
    let injector: TestBed;
    let service: TypeBonComService;
    let httpMock: HttpTestingController;
    let elemDefault: ITypeBonCom;
    let expectedResult: ITypeBonCom | ITypeBonCom[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TypeBonComService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TypeBonCom(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TypeBonCom', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new TypeBonCom()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TypeBonCom', () => {
        const returnedFromService = Object.assign(
          {
            libelleTypeBonCom: 'BBBBBB',
            codeTypeBonCom: 'BBBBBB',
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TypeBonCom', () => {
        const returnedFromService = Object.assign(
          {
            libelleTypeBonCom: 'BBBBBB',
            codeTypeBonCom: 'BBBBBB',
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TypeBonCom', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
