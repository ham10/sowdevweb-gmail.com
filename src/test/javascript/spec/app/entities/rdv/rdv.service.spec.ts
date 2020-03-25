import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RDVService } from 'app/entities/rdv/rdv.service';
import { IRDV, RDV } from 'app/shared/model/rdv.model';

describe('Service Tests', () => {
  describe('RDV Service', () => {
    let injector: TestBed;
    let service: RDVService;
    let httpMock: HttpTestingController;
    let elemDefault: IRDV;
    let expectedResult: IRDV | IRDV[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RDVService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RDV(0, 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateRdv: currentDate.format(DATE_FORMAT),
            heureRdv: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RDV', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateRdv: currentDate.format(DATE_FORMAT),
            heureRdv: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateRdv: currentDate,
            heureRdv: currentDate
          },
          returnedFromService
        );

        service.create(new RDV()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RDV', () => {
        const returnedFromService = Object.assign(
          {
            numRdv: 'BBBBBB',
            dateRdv: currentDate.format(DATE_FORMAT),
            heureRdv: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateRdv: currentDate,
            heureRdv: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RDV', () => {
        const returnedFromService = Object.assign(
          {
            numRdv: 'BBBBBB',
            dateRdv: currentDate.format(DATE_FORMAT),
            heureRdv: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateRdv: currentDate,
            heureRdv: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RDV', () => {
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
