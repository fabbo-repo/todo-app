import { AxiosResponse } from "axios";
import { HttpError, HttpErrorEnum } from "../errors/http-error";
import { ApiCodeError } from "../errors/api-code-error";
import { Either } from "../either";

export const axiosResponseToEither = <T>(
  httpResponse: AxiosResponse
): Either<HttpError, T> => {
  if (~~(httpResponse.status / 10) === 20) {
    return Either.right(httpResponse.data);
  }
  return Either.left(axiosResponseToError(httpResponse));
};

export const axiosResponseToError = (
  httpResponse: AxiosResponse
): HttpError => {
  if (httpResponse.status === 400) {
    if (httpResponse.data.errorCode) {
      return new ApiCodeError(httpResponse.data.errorCode);
    }
    return new HttpError(HttpErrorEnum.BAD_ERROR_REQUEST);
  }
  if (httpResponse.status === 401) {
    return new HttpError(HttpErrorEnum.UNAUTHORIZED_ERROR_REQUEST);
  }
  if (httpResponse.status === 403) {
    return new HttpError(HttpErrorEnum.FORBIDDEN_ERROR_REQUEST);
  }
  return new HttpError(HttpErrorEnum.SERVICE_ERROR_REQUEST);
};
