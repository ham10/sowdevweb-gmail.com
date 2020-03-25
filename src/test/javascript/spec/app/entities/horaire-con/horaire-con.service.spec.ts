import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { HoraireConService } from 'app/entities/horaire-con/horaire-con.service';
import { IHoraireCon, HoraireCon } from 'app/shared/model/horaire-con.model';

describe('Service Tests', () => {
  describe('HoraireCon Service', () => {
    let injector: TestBed;
    let service: HoraireConService;
    let httpMock: HttpTestingController;
    let elemDefault: IHoraireCon;
    let expectedResult: IHoraireCon | IHoraireCon[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HoraireConService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new HoraireCon(0, currentDate, currentDate, currentDate, currentDate, currentDate, currentDate, currentDate, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            heureDebutHC: currentDate.format(DATE_FORMAT),
            heureFinHC: currentDate.format(DATE_FORMAT),
            dateDebutHC: currentDate.format(DATE_FORMAT),
            dateFinHC: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a HoraireCon', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            heureDebutHC: currentDate.format(DATE_FORMAT),
            heureFinHC: currentDate.format(DATE_FORMAT),
            dateDebutHC: currentDate.format(DATE_FORMAT),
            dateFinHC: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebutHC: currentDate,
            heureFinHC: currentDate,
            dateDebutHC: currentDate,
            dateFinHC: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate
          },
          returnedFromService
        );

        service.create(new HoraireCon()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HoraireCon', () => {
        const returnedFromService = Object.assign(
          {
            heureDebutHC: currentDate.format(DATE_FORMAT),
            heureFinHC: currentDate.format(DATE_FORMAT),
            dateDebutHC: currentDate.format(DATE_FORMAT),
            dateFinHC: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebutHC: currentDate,
            heureFinHC: currentDate,
            dateDebutHC: currentDate,
            dateFinHC: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HoraireCon', () => {
        const returnedFromService = Object.assign(
          {
            heureDebutHC: currentDate.format(DATE_FORMAT),
            heureFinHC: currentDate.format(DATE_FORMAT),
            dateDebutHC: currentDate.format(DATE_FORMAT),
            dateFinHC: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDebutHC: currentDate,
            heureFinHC: currentDate,
            dateDebutHC: currentDate,
            dateFinHC: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a HoraireCon', () => {
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
