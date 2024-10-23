import React, { FocusEventHandler, HTMLInputTypeAttribute } from "react";
import "./app-date-field.css";

interface AppDateFieldProps {
  id?: string;
  date?: Date;
  isDateTime?: boolean;
  type?: HTMLInputTypeAttribute;
  onDateChange: (newDate: Date) => void;
  errorText?: string;
  isRequired?: boolean;
  onBlur?: FocusEventHandler | undefined;
}

const AppDateField: React.FC<AppDateFieldProps> = ({
  id,
  date,
  isDateTime,
  onDateChange,
  errorText,
  isRequired,
  onBlur,
}) => {
  let dateAsText: string = "";
  try {
    dateAsText = isDateTime
      ? date?.toISOString().slice(0, 16) ?? ""
      : date?.toISOString().split("T")[0] ?? "";
  } catch {
    // Ignore catch
  }
  return (
    <div className="app-date-field">
      <input
        id={id}
        type={isDateTime ? "datetime-local" : "date"}
        value={dateAsText}
        onChange={(e) => onDateChange(new Date(e.target.value))}
        className={errorText ? "error" : ""}
        required={isRequired}
        onBlur={onBlur}
      />
      {errorText && <p className="error">{errorText}</p>}
    </div>
  );
};

export default AppDateField;
