import {Pipe, PipeTransform} from '@angular/core';
import {DatePipe, Time} from "@angular/common";

@Pipe({
  name: 'time',
  standalone: true
})
export class TimePipe implements PipeTransform {
  transform(time: Time, args?: any): string | null {
    const datePipe = new DatePipe('ru-RU');
    const timeAsDate = Date.parse(`01-01-1970 ${time}`);
    return datePipe.transform(timeAsDate, 'HH:mm');
  }
}
