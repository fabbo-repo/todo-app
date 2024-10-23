import React, { FocusEventHandler, HTMLInputTypeAttribute } from "react";
import "./app-text-field.css";

interface AppTextFieldProps {
  id?: string;
  text: string;
  type?: HTMLInputTypeAttribute;
  onTextChange: (newText: string) => void;
  errorText?: string;
  maxLength?: number;
  isRequired?: boolean;
  onBlur?: FocusEventHandler | undefined;
}

const AppTextField: React.FC<AppTextFieldProps> = ({
  id,
  text,
  type,
  onTextChange,
  errorText,
  maxLength,
  isRequired,
  onBlur,
}) => {
  return (
    <div className="app-text-field">
      <input
        type={type ?? "text"}
        id={id}
        value={text}
        onChange={(e) => onTextChange(e.target.value)}
        className={errorText ? "error" : ""}
        maxLength={maxLength}
        required={isRequired}
        onBlur={onBlur}
      />
      {errorText && <p className="error">{errorText}</p>}
      {maxLength && (
        <p
          className={`app-text-field-char-count ${
            text.length > maxLength ? "error" : ""
          }`}
        >
          {text.length}/{maxLength}
        </p>
      )}
    </div>
  );
};

export default AppTextField;
