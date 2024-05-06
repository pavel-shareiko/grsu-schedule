export class Pagination {
  constructor(public page: number,
              public rowsPerPage: number,
              public totalPages: number,
              public totalElements: number
  ) {
  }
}
