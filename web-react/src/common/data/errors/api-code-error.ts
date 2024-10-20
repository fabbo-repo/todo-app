import { HttpError, HttpErrorEnum } from './http-error';

export class ApiCodeError extends HttpError {
  errorCode: string;

  constructor(errorCode: string) {
    super(HttpErrorEnum.BAD_ERROR_REQUEST);
    this.errorCode = errorCode;
  }
}
