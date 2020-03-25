import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { VaccinService } from 'app/entities/vaccin/vaccin.service';
import { IVaccin, Vaccin } from 'app/shared/model/vaccin.model';

describe('Service Tests', () => {
  describe('Vaccin Service', () => {
    let injector: TestBed;
    let service: VaccinService;
    let httpMock: HttpTestingController;
    let elemDefault: IVaccin;
    let expectedResult: IVaccin | IVaccin[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VaccinService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Vaccin(0, 'AAAAAAA', currentDate, currentDate, currentDate, currentDate, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
            dateRenouv: currentDate.format(DATE_FORMAT),
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

      it('should create a Vaccin', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
            dateRenouv: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            dateRenouv: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new Vaccin()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Vaccin', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            dateRenouv: currentDate.format(DATE_FORMAT),
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
            date: currentDate,
            dateRenouv: currentDate,
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

      it('should return a list of Vaccin', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            dateRenouv: currentDate.format(DATE_FORMAT),
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
            date: currentDate,
            dateRenouv: currentDate,
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

      it('should delete a Vaccin', () => {
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
