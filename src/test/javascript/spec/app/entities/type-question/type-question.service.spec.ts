import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TypeQuestionService } from 'app/entities/type-question/type-question.service';
import { ITypeQuestion, TypeQuestion } from 'app/shared/model/type-question.model';

describe('Service Tests', () => {
  describe('TypeQuestion Service', () => {
    let injector: TestBed;
    let service: TypeQuestionService;
    let httpMock: HttpTestingController;
    let elemDefault: ITypeQuestion;
    let expectedResult: ITypeQuestion | ITypeQuestion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TypeQuestionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TypeQuestion(0, 'AAAAAAA', 'AAAAAAA', 0, currentDate, currentDate, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDeleted: currentDate.format(DATE_TIME_FORMAT),
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TypeQuestion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDeleted: currentDate.format(DATE_TIME_FORMAT),
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeleted: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new TypeQuestion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TypeQuestion', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            belleType: 'BBBBBB',
            multipliciteChoix: 1,
            dateDeleted: currentDate.format(DATE_TIME_FORMAT),
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT),
            userUpdate: 1,
            userDelete: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeleted: currentDate,
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

      it('should return a list of TypeQuestion', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            belleType: 'BBBBBB',
            multipliciteChoix: 1,
            dateDeleted: currentDate.format(DATE_TIME_FORMAT),
            dateCreated: currentDate.format(DATE_TIME_FORMAT),
            dateUpdated: currentDate.format(DATE_TIME_FORMAT),
            userUpdate: 1,
            userDelete: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeleted: currentDate,
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

      it('should delete a TypeQuestion', () => {
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
