import {Injectable} from '@angular/core';
import {ActiveToast, IndividualConfig, ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private toast: ToastrService) {
  }

  public showSuccess(message: string, title: string = 'Успех', config?: Partial<IndividualConfig>): ActiveToast<any> {
    return this.toast.success(message, title, config);
  }

  public showError(message: string, title: string = 'Ошибка', config?: Partial<IndividualConfig>): ActiveToast<any> {
    return this.toast.error(message, title, config);
  }

  public showInfo(message: string, title: string = 'Инфо', config?: Partial<IndividualConfig>): ActiveToast<any> {
    return this.toast.info(message, title, config);
  }

  public showWarning(message: string, title: string = 'Внимание', config?: Partial<IndividualConfig>): ActiveToast<any> {
    return this.toast.warning(message, title, config);
  }

  public clear(): void {
    this.toast.clear();
  }
}
