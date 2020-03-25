import { Component, OnInit, ChangeDetectionStrategy, ViewChild, TemplateRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlanning, Planning } from 'app/shared/model/planning.model';
import { PlanningService } from './planning.service';
import { IMedecin } from 'app/shared/model/medecin.model';
import { MedecinService } from 'app/entities/medecin/medecin.service';
import { ITypePlanning } from 'app/shared/model/type-planning.model';
import { TypePlanningService } from 'app/entities/type-planning/type-planning.service';

import { startOfDay, endOfDay, isSameDay, isSameMonth } from 'date-fns';
import { Subject } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent, CalendarView } from 'angular-calendar';
import { DetailPlanning, IDetailPlanning } from 'app/shared/model/detail-planning.model';
import * as moment from 'moment';
import { DetailPlanningService } from 'app/entities/detail-planning/detail-planning.service';
import { EtatPlanningService } from 'app/entities/etat-planning/etat-planning.service';
import { IEtatPlanning } from 'app/shared/model/etat-planning.model';
type SelectableEntity = IMedecin | ITypePlanning;
const colors: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3'
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF'
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA'
  }
};

@Component({
  selector: 'jhi-planning-update',
  templateUrl: './planning-update.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['./planning-update.component.scss']
})
export class PlanningUpdateComponent implements OnInit {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any> | null = null;

  view: CalendarView = CalendarView.Month;

  CalendarView = CalendarView;

  viewDate: Date = new Date();
  etatPlannings: IEtatPlanning[] = [];
  // detailPlannings: IDetailPlanning[]  = [];
  detailPlanning: IDetailPlanning = new DetailPlanning();
  modalData: {
    action: string;
    event: CalendarEvent;
  } | null = null;

  actions: CalendarEventAction[] = [
    {
      label: '<i class="fa fa-fw fa-pencil"></i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      }
    },
    {
      label: '<i class="fa fa-fw fa-times"></i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.events.filter(iEvent => iEvent !== event);
        this.handleEvent('Deleted', event);
      }
    }
  ];

  refresh: Subject<any> = new Subject();

  events: CalendarEvent[] = [
    // {
    //   start: subDays(startOfDay(new Date()), 1),
    //   end: addDays(new Date(), 1),
    //   title: 'A 3 day event',
    //   color: colors.red,
    //   actions: this.actions,
    //   allDay: true,
    //   resizable: {
    //     beforeStart: true,
    //     afterEnd: true
    //   },
    //   draggable: true
    // },
    // {
    //   start: startOfDay(new Date()),
    //   title: 'An event with no end date',
    //   color: colors.yellow,
    //   actions: this.actions
    // },
    // {
    //   start: subDays(endOfMonth(new Date()), 3),
    //   end: addDays(endOfMonth(new Date()), 3),
    //   title: 'A long event that spans 2 months',
    //   color: colors.blue,
    //   allDay: true
    // },
    // {
    //   start: addHours(startOfDay(new Date()), 2),
    //   end: addHours(new Date(), 2),
    //   title: 'A draggable and resizable event',
    //   color: colors.yellow,
    //   actions: this.actions,
    //   resizable: {
    //     beforeStart: true,
    //     afterEnd: true
    //   },
    //   draggable: true
    // }
  ];

  activeDayIsOpen = true;
  // planning: null =  null;
  isSaving = false;
  medecins: IMedecin[] = [];
  typeplannings: ITypePlanning[] = [];
  dateCreatedDp: any;

  editForm = this.fb.group({
    id: [],
    num: [],
    libelle: [],
    dateCreated: [],
    medecin: [],
    typePlanning: []
  });

  constructor(
    protected planningService: PlanningService,
    protected medecinService: MedecinService,
    protected typePlanningService: TypePlanningService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private modal: NgbModal,
    private detailPlanningService: DetailPlanningService,
    private etatPlanningService: EtatPlanningService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planning }) => {
      this.updateForm(planning);

      this.medecinService.query().subscribe((res: HttpResponse<IMedecin[]>) => {
        this.medecins = res.body || [];
      });

      this.typePlanningService.query().subscribe((res: HttpResponse<ITypePlanning[]>) => (this.typeplannings = res.body || []));
    });
    this.etatPlanningService.query().subscribe((res: HttpResponse<IEtatPlanning[]>) => (this.etatPlannings = res.body || []));
  }

  updateForm(planning: IPlanning): void {
    this.editForm.patchValue({
      id: planning.id,
      num: planning.num,
      libelle: planning.libelle,
      dateCreated: planning.dateCreated,
      medecin: planning.medecin,
      typePlanning: planning.typePlanning
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planning = this.createFromForm();
    if (planning.id !== undefined) {
      this.subscribeToSaveResponse(this.planningService.update(planning));
    } else {
      this.planningService.create(planning).subscribe(
        (res: HttpResponse<IPlanning>) => {
          const p: IPlanning | null = res.body;
          if (p != null) {
            this.events.map(e => {
              this.detailPlanning.dateDebut = moment(e.start);
              this.detailPlanning.titre = e.title;
              this.detailPlanning.dateFin = moment(e.end);

              this.detailPlanning.planning = p;

              this.detailPlanning.etatPlanning = e.meta;

              this.saveDetailPlanning(this.detailPlanning);
            });
          }
          this.onSaveSuccess();
        },
        () => this.onSaveError()
      );

      this.planningService.create(planning).subscribe(
        (res: HttpResponse<IPlanning>) => {
          const p: IPlanning | null = res.body;
          if (p != null) {
            this.events.map(e => {
              this.detailPlanning.dateDebut = moment(e.start);
              this.detailPlanning.titre = e.title;
              this.detailPlanning.dateFin = moment(e.end);
              this.detailPlanning.planning = p;

              this.detailPlanning.etatPlanning = e.meta;

              this.saveDetailPlanning(this.detailPlanning);
            });
          }
          this.onSaveSuccess();
        },
        () => this.onSaveError()
      );

      // const idplanning = this.planning == null? 0: this.planning.id;
    }
  }

  private createFromForm(): IPlanning {
    return {
      ...new Planning(),
      id: this.editForm.get(['id'])!.value,
      num: this.editForm.get(['num'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      medecin: this.editForm.get(['medecin'])!.value,
      typePlanning: this.editForm.get(['typePlanning'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanning>>): void {
    result.subscribe(
      () => {
        // this.planning = res.body;
        this.onSaveSuccess();
      },
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if ((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({ event, newStart, newEnd }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map(iEvent => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    this.modalData = { event, action };
    this.modal.open(this.modalContent, { size: 'lg' });
  }

  addEvent(): void {
    this.events = [
      ...this.events,
      {
        title: 'New event',
        start: startOfDay(new Date()),
        end: endOfDay(new Date()),
        color: colors.red,
        draggable: true,
        resizable: {
          beforeStart: true,
          afterEnd: true
        }
      }
    ];
  }

  deleteEvent(eventToDelete: CalendarEvent): void {
    this.events = this.events.filter(event => event !== eventToDelete);
  }

  setView(view: CalendarView): void {
    this.view = view;
  }

  closeOpenMonthViewDay(): void {
    this.activeDayIsOpen = false;
  }

  saveDetailPlanning(detailPlanning: IDetailPlanning): void {
    this.detailPlanningService.create(detailPlanning).subscribe();
  }
}
