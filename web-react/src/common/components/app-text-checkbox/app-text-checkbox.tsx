import React from "react";
import "./app-text-checkbox.css";

interface AppTextCheckboxProps {
  id?: string;
  text: string;
  isChecked: boolean;
  onChange: (value: boolean) => void;
}

const AppTextCheckbox: React.FC<AppTextCheckboxProps> = ({
  id,
  text,
  isChecked,
  onChange,
}) => {
  return (
    <div className="app-text-checkbox">
      <input
        type="checkbox"
        id={id}
        checked={isChecked}
        onChange={(e) => onChange(e.target.checked)}
      />
      <label htmlFor="completed">{text}</label>
    </div>
  );
};

export default AppTextCheckbox;
