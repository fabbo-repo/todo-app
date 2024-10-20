import { AppError } from "./app-error";

export class NetworkError extends AppError {
  netErrorType: NetworkErrorTypeEnum;

  constructor(type: NetworkErrorTypeEnum) {
    super("");
    this.netErrorType = type;
  }

  static connectionError(): NetworkError {
    return new NetworkError(NetworkErrorTypeEnum.CONNECTION_ERROR);
  }
}

export enum NetworkErrorTypeEnum {
  CONNECTION_ERROR,
  UNKNOWN_ERROR,
}
