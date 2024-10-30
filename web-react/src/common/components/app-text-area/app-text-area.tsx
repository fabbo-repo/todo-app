import React, { FocusEventHandler } from "react";
import "./app-text-area.css";

interface AppTextAreaProps {
  id?: string;
  text: string;
  onTextChange: (newText: string) => void;
  errorText?: string;
  maxLength?: number;
  onBlur?: FocusEventHandler;
  rows?: number;
}

const AppTextArea: React.FC<AppTextAreaProps> = ({
  id,
  text,
  onTextChange,
  errorText,
  maxLength,
  onBlur,
  rows,
}) => {
  return (
    <div className="app-text-area">
      <textarea
        id={id}
        value={text}
        onChange={(e) => onTextChange(e.target.value)}
        className={errorText ? "error" : ""}
        maxLength={maxLength}
        onBlur={onBlur}
        rows={rows}
      />
      {errorText && <p className="error">{errorText}</p>}
      {maxLength && (
        <p
          className={`app-text-area-char-count ${
            text.length > (maxLength ?? Infinity) ? "error" : ""
          }`}
        >
          {text.length}/{maxLength}
        </p>
      )}
    </div>
  );
};

export default AppTextArea;
