import React from "react";
import { ArrowLeft } from "lucide-react";
import "./app-back-button.css";

interface AppBackButtonProps {
  onClick: () => void;
}

const AppBackButton: React.FC<AppBackButtonProps> = ({ onClick }) => {
  return (
    <button onClick={onClick} className="app-back-button">
      <ArrowLeft size={24} />
    </button>
  );
};

export default AppBackButton;
