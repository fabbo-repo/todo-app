import React from "react";
import "./app-text-button.css";

interface AppTextButtonProps {
  text: string;
  onClick?: () => void;
  isSubmit?: boolean;
}

const AppTextButton: React.FC<AppTextButtonProps> = ({
  text,
  onClick,
  isSubmit,
}) => {
  return (
    <>
      <button
        type={isSubmit ? "submit" : "button"}
        className="app-text-button"
        onClick={onClick}
      >
        {text}
      </button>
    </>
  );
};

export default AppTextButton;
