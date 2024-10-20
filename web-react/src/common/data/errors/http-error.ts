import { NetworkError, NetworkErrorTypeEnum } from "./network-error";

export class HttpError extends NetworkError {
  httpErrorType: HttpErrorEnum;

  constructor(type: HttpErrorEnum) {
    super(NetworkErrorTypeEnum.UNKNOWN_ERROR);
    this.httpErrorType = type;
  }
}

export enum HttpErrorEnum {
  BAD_ERROR_REQUEST,
  UNAUTHORIZED_ERROR_REQUEST,
  FORBIDDEN_ERROR_REQUEST,
  SERVICE_ERROR_REQUEST,
}
