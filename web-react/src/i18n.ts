import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import { enTranslations } from "./i18n/i18n-en";
import { esTranslations } from "./i18n/i18n-es";

i18n
  .use(initReactI18next)
  .use(LanguageDetector)
  .init({
    fallbackLng: "en",
    detection: {
      order: ["navigator"],
    },
    supportedLngs: ["en", "es"],
    debug: true,
    interpolation: {
      escapeValue: false,
    },
    resources: {
      en: enTranslations,
      es: esTranslations,
    },
  });

export default i18n;
