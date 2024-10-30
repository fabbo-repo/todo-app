import { TFunction } from "i18next";

export function isEmail(email: string): boolean {
  const regEmail =
    /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return regEmail.test(email);
}

export function localeToText(
  locale: string,
  t: TFunction<"translation", undefined>
): string {
  switch (locale) {
    case "en":
      return t("locale.english");
    case "es":
      return t("locale.spanish");
    default:
      return "-";
  }
}
