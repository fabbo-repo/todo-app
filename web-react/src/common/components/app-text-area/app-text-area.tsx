import React from "react";
import "./app-text-area.css";

interface AppTextAreaProps {
  id?: string;
  text: string;
  onTextChange: (newText: string) => void;
  maxCounterNum?: number;
  errorText?: string;
  maxLength?: number;
}

const AppTextArea: React.FC<AppTextAreaProps> = ({
  id,
  text,
  onTextChange,
  maxCounterNum,
  errorText,
  maxLength,
}) => {
  return (
    <div className="app-text-area">
      <textarea
        id={id}
        value={text}
        onChange={(e) => onTextChange(e.target.value)}
        className={errorText ? "error" : ""}
        maxLength={maxLength}
      />
      {errorText && <p className="error">{errorText}</p>}
      {maxCounterNum && (
        <p
          className={`app-text-field-char-count ${
            text.length > (maxLength ?? Infinity) ? "error" : ""
          }`}
        >
          {text.length}/{maxCounterNum}
        </p>
      )}
    </div>
  );
};

export default AppTextArea;
