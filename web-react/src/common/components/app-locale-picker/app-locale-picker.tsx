import React from "react";
import "./app-locale-picker.css";
import { localeToText } from "../../utils/form-utils";
import { useTranslation } from "react-i18next";

interface AppLocalePickerProps {
  id?: string;
  locale: string;
  onLocaleChange: (newLocale: string) => void;
}

const AppLocalePicker: React.FC<AppLocalePickerProps> = ({
  id,
  locale,
  onLocaleChange,
}) => {
  const { t } = useTranslation();

  const filteredLocale: string = locale.slice(0, 2);

  return (
    <select
      id={id}
      value={filteredLocale}
      onChange={(e) => onLocaleChange(e.target.value as "es" | "en")}
      className="app-locale-picker"
    >
      <option value="es">{localeToText("es", t)}</option>
      <option value="en">{localeToText("en", t)}</option>
    </select>
  );
};

export default AppLocalePicker;
