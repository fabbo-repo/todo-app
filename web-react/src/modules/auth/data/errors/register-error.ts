import { AppError } from "../../../../common/data/errors/app-error";

export class RegisterError extends AppError {
  type: RegisterErrorTypeEnum;

  constructor(type: RegisterErrorTypeEnum) {
    super("");
    this.type = type;
  }

  static weakPasswordError(): RegisterError {
    return new RegisterError(RegisterErrorTypeEnum.WEAK_PASSWORD_ERROR);
  }

  static unknownError(): RegisterError {
    return new RegisterError(RegisterErrorTypeEnum.UNKNOWN_ERROR);
  }
}

export enum RegisterErrorTypeEnum {
  WEAK_PASSWORD_ERROR,
  UNKNOWN_ERROR,
}
