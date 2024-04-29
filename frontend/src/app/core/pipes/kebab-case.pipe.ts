import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'kebabCase',
  standalone: true
})
export class KebabCasePipe implements PipeTransform {

  transform(value: string): string {
    let result = '';
    for (let i = 0; i < value.length; i++) {
      const char = value[i];
      if (char === char.toUpperCase()) {
        if (i !== 0) {
          result += '-';
        }
        result += char.toLowerCase();
      } else {
        result += char;
      }
    }
    return result;
  }

}
