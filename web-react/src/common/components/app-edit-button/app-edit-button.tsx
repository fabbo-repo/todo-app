import React from "react";
import "./app-edit-button.css";
import { Edit } from "lucide-react";

interface AppEditButtonProps {
  onClick: () => void;
}

const AppEditButton: React.FC<AppEditButtonProps> = ({ onClick }) => {
  return (
    <button onClick={onClick} className="app-edit-button">
      <Edit size={24} />
    </button>
  );
};

export default AppEditButton;
