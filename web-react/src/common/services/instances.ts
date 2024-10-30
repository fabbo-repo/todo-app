import { InternalAxiosRequestConfig } from "axios";
import HttpService from "./http/http-service";
import { firebaseAuth } from "../config/firebase";

class Session {
  private _accessToken: string | null = null;

  public get accessToken(): string | null {
    return this._accessToken;
  }

  public set accessToken(accessToken: string | null) {
    this._accessToken = accessToken;
  }

  public get isLoggedIn(): boolean {
    return !!this._accessToken;
  }
}

export const sessionInstance = new Session();

firebaseAuth.onAuthStateChanged(function (user) {
  if (user) {
    user.getIdToken().then(function (idToken) {
      sessionInstance.accessToken = idToken;
    });
  } else {
    sessionInstance.accessToken = null;
  }
});

export const taskApiServiceInstance = new HttpService(
  { "Content-Type": "application/json" },
  import.meta.env.VITE_TASK_API_BASE_URL!,
  (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    const accessToken = sessionInstance.accessToken;
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  undefined,
  undefined,
  undefined
);
