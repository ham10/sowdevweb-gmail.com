import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ParamDiversService } from 'app/entities/param-divers/param-divers.service';
import { IParamDivers, ParamDivers } from 'app/shared/model/param-divers.model';

describe('Service Tests', () => {
  describe('ParamDivers Service', () => {
    let injector: TestBed;
    let service: ParamDiversService;
    let httpMock: HttpTestingController;
    let elemDefault: IParamDivers;
    let expectedResult: IParamDivers | IParamDivers[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ParamDiversService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ParamDivers(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        false,
        currentDate,
        currentDate,
        0,
        0,
        0
      );
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

      it('should create a ParamDivers', () => {
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

        service.create(new ParamDivers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ParamDivers', () => {
        const returnedFromService = Object.assign(
          {
            codeParamDivers: 'BBBBBB',
            libelleParamDivers: 'BBBBBB',
            descriptionParamDivers: 'BBBBBB',
            valeurNum1: 1,
            valeurNum2: 1,
            valeurNum3: 1,
            valeurText1: 'BBBBBB',
            valeurText2: 'BBBBBB',
            valeurText3: 'BBBBBB',
            valeurBoolean1: true,
            valeurBoolean2: true,
            valeurBoolean3: true,
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

      it('should return a list of ParamDivers', () => {
        const returnedFromService = Object.assign(
          {
            codeParamDivers: 'BBBBBB',
            libelleParamDivers: 'BBBBBB',
            descriptionParamDivers: 'BBBBBB',
            valeurNum1: 1,
            valeurNum2: 1,
            valeurNum3: 1,
            valeurText1: 'BBBBBB',
            valeurText2: 'BBBBBB',
            valeurText3: 'BBBBBB',
            valeurBoolean1: true,
            valeurBoolean2: true,
            valeurBoolean3: true,
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

      it('should delete a ParamDivers', () => {
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
