import {Pagination} from "../../../core/types/pagination";

export class AnalysisResult {
  constructor(public id: number,
              public moduleName: string,
              public context: string,
              public result: string,
              public status: string,
              public createTimestamp: Date,
              public createdBy: string
  ) {
  }
}

export class AnalysisResultSearchResponse {
  constructor(public payload: AnalysisResult[],
              public pagination: Pagination
  ) {
  }
}
