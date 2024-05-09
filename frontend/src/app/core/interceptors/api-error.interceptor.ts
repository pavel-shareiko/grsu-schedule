import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {NotificationService} from "../services/notification.service";
import {environment} from "../../../environments/environment";

@Injectable()
export class ApiErrorInterceptor implements HttpInterceptor {
  constructor(private notification: NotificationService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        const toast = this.notification.showError(ApiErrorInterceptor.buildErrorDetails(error), 'Ошибка', {
          progressBar: true,
          payload: {
            error
          }
        });

        if (!environment.production) {
          toast.onTap.subscribe(() => {
            this.notification.showInfo(ApiErrorInterceptor.buildDebuggingErrorDetails(error), "Инфо", {
              enableHtml: true,
              positionClass: 'toast-bottom-full-width',
            })
          })
        }

        return throwError(error)
      })
    );
  }

  private static buildDebuggingErrorDetails(error: HttpErrorResponse) {
    return `
Сообщение: <pre>${error.message}</pre><br>
Детали: <pre>${JSON.stringify(error.error, null, 2)}</pre>
`;
  }

  private static buildErrorDetails(error: HttpErrorResponse) {
    return `Во время выполнения запроса произошла ошибка (HTTP ${error.status})`;
  }
}
