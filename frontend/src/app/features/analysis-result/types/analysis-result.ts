import {Pagination} from "../../../core/types/pagination";

export class AnalysisResult {
  constructor(public id: number,
              public moduleName: string,
              public context: any,
              public result: any,
              public status: string,
              public createTimestamp: Date,
              public createdBy: string
  ) {
  }

  public static fromJson(json: any): AnalysisResult {
    return new AnalysisResult(
      json.id,
      json.moduleName,
      json.context,
      json.result,
      json.status,
      new Date(json.createTimestamp),
      json.createdBy
    );
  }

  public isSuccessful(): boolean {
    return this.status === 'SUCCESS';
  }

  public isError(): boolean {
    return this.status === 'ERROR';
  }
}

export class AnalysisResultSearchResponse {
  constructor(public payload: AnalysisResult[],
              public pagination: Pagination
  ) {
  }
}
