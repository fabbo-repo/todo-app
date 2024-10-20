import React from "react";
import { useNavigate } from "react-router-dom";
import "./app-error-view.css";
import { useTranslation } from "react-i18next";
import { ROOT_ROUTE_PATH } from "../../../modules/task/routes";

interface AppErrorViewProps {
  title: string;
  message?: string;
}

const AppErrorView: React.FC<AppErrorViewProps> = ({ title, message }) => {
  const { t } = useTranslation();

  const navigate = useNavigate();

  const handleGoHome = () => {
    navigate(ROOT_ROUTE_PATH);
  };

  return (
    <div className="app-error-view-container">
      <h2 className="app-error-view-title">${title}</h2>
      <p className="app-error-view-text">${message}</p>
      <button onClick={handleGoHome} className="app-error-view-button">
        {t("error.goToHome")}
      </button>
    </div>
  );
};

export default AppErrorView;
