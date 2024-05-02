export class ResourceDescription {
  constructor(public url: string,
              public contentPath: string,
              public paramName: string,
              public identifier: string,
              public multiselect: boolean,
              public displayFormat: string) {
  }

  public static fromString(resourceDescriptionString: string): ResourceDescription {
    const parts = resourceDescriptionString.split('$$');
    const res: any = {};
    for (const part of parts) {
      const [key, value] = part.split('=');
      if (key === 'multiselect') {
        res[key] = value ? Boolean(value === 'true') : false;
      } else {
        res[key] = value;
      }
    }

    return res as ResourceDescription;
  }
}
