import {Component, ViewChild} from '@angular/core';
import {DatePipe} from "@angular/common";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {MatExpansionPanel, MatExpansionPanelHeader, MatExpansionPanelTitle} from "@angular/material/expansion";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Router} from "@angular/router";
import {Teacher} from "../../teachers/types/teacher";
import {SubjectService} from "../service/subject.service";
import {MatCheckbox} from "@angular/material/checkbox";
import {MatOption, MatSelect, MatSelectTrigger} from "@angular/material/select";
import {SubjectSearchFilter} from "../type/subject";

@Component({
  selector: 'app-subjects-list',
  standalone: true,
  imports: [
    DatePipe,
    FormsModule,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatFormField,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatIcon,
    MatInput,
    MatLabel,
    MatSelectTrigger,
    MatPaginator,
    MatRow,
    MatRowDef,
    MatTable,
    ReactiveFormsModule,
    MatHeaderCellDef,
    MatCheckbox,
    MatSelect,
    MatOption
  ],
  templateUrl: './subjects-list.component.html',
  styleUrl: './subjects-list.component.scss'
})
export class SubjectsListComponent {
  displayedColumns: string[] = ['id', 'title', 'createTimestamp', 'subjectCard'];
  subjectCardPresenceOptions = [
    {value: 'ANY', displayValue: 'Не важно'},
    {value: 'PRESENT', displayValue: 'Существует'},
    {value: 'MISSING', displayValue: 'Отсутствует'}
  ];
  selectedSubjectCardPresenceOption = this.subjectCardPresenceOptions[0].value;
  dataSource!: MatTableDataSource<any>;
  pageSize: number = 10;
  pageIndex: number = 0;
  totalElements: number = 0;

  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator;

  public form!: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private subjectService: SubjectService,
              private router: Router) {
  }

  ngOnInit() {
    this.loadData();

    this.form = this.formBuilder.group({
      id: [null],
      title: [null],
      subjectCardPresence: [this.subjectCardPresenceOptions[0].value],
    });
  }

  loadData() {
    const filter = this.form?.value as SubjectSearchFilter;
    this.subjectService.getSubjects(filter, this.pageIndex, this.pageSize).subscribe(response => {
      this.dataSource = new MatTableDataSource(response.payload);
      this.totalElements = response.pagination.totalElements;

      this.dataSource.filterPredicate = (data: any, filter: string) => {
        const dataStr = JSON.stringify(data).toLowerCase();
        const filterStr = filter.trim().toLowerCase();
        return dataStr.indexOf(filterStr) != -1;
      };
    });
  }

  onPageChange($event: PageEvent) {
    this.pageIndex = $event.pageIndex;
    this.pageSize = $event.pageSize;
    this.loadData();
  }

  applyFilter() {
    this.paginator.firstPage();
    this.loadData();
  }

  onRowClick(row: Teacher) {
    this.router.navigate(['/subjects', row.id]);
  }
}
