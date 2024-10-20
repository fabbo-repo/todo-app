import axios, {
  AxiosError,
  AxiosHeaderValue,
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from "axios";
import { Either } from "../../data/either";
import { axiosResponseToEither } from "../../data/mappers/http-mappers";
import { NetworkError } from "../../data/errors/network-error";

class HttpService {
  private _axiosInstance: AxiosInstance;

  public constructor(
    headers: Record<string, AxiosHeaderValue>,
    baseUrl: string,
    requestInterceptorOnSuccess?: (
      config: InternalAxiosRequestConfig
    ) => InternalAxiosRequestConfig,
    requestInterceptorOnError?: (error: AxiosError) => Promise<AxiosError>,
    responseInterceptorOnSuccess?: (response: AxiosResponse) => AxiosResponse,
    responseInterceptorOnError?: (error: AxiosError) => Promise<AxiosError>
  ) {
    this._axiosInstance = axios.create({
      baseURL: baseUrl,
      headers: headers,
      withCredentials: true,
    });

    this._axiosInstance.interceptors.request.use(
      requestInterceptorOnSuccess,
      requestInterceptorOnError
    );

    this._axiosInstance.interceptors.response.use(
      responseInterceptorOnSuccess,
      responseInterceptorOnError
    );
  }

  public get axiosInstance(): AxiosInstance {
    return this._axiosInstance;
  }

  public async getRequest<T>(
    endpoint: string,
    config?: AxiosRequestConfig
  ): Promise<Either<NetworkError, T>> {
    try {
      const response = await this._axiosInstance.get<T>(endpoint, config);
      return axiosResponseToEither<T>(response);
    } catch {
      return Either.left(NetworkError.connectionError());
    }
  }

  public async postRequest<V, T>(
    endpoint: string,
    data: V,
    config?: AxiosRequestConfig
  ): Promise<Either<NetworkError, T>> {
    try {
      const response = await this._axiosInstance.post<T>(
        endpoint,
        data,
        config
      );
      return axiosResponseToEither<T>(response);
    } catch {
      return Either.left(NetworkError.connectionError());
    }
  }

  public async putRequest<V, T>(
    endpoint: string,
    data: V,
    config?: AxiosRequestConfig
  ): Promise<Either<NetworkError, T>> {
    try {
      const response = await this._axiosInstance.put<T>(endpoint, data, config);
      return axiosResponseToEither<T>(response);
    } catch {
      return Either.left(NetworkError.connectionError());
    }
  }

  public async patchRequest<V, T>(
    endpoint: string,
    data: V,
    config?: AxiosRequestConfig
  ): Promise<Either<NetworkError, T>> {
    try {
      const response = await this._axiosInstance.patch<T>(
        endpoint,
        data,
        config
      );
      return axiosResponseToEither<T>(response);
    } catch {
      return Either.left(NetworkError.connectionError());
    }
  }

  public async deleteRequest<T>(
    endpoint: string,
    config?: AxiosRequestConfig
  ): Promise<Either<NetworkError, T>> {
    try {
      const response = await this._axiosInstance.delete<T>(endpoint, config);
      return axiosResponseToEither<T>(response);
    } catch {
      return Either.left(NetworkError.connectionError());
    }
  }
}

export default HttpService;
