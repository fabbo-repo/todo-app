import React from "react";
import "./app-error-view.css";
import { useTranslation } from "react-i18next";
import { ROOT_ROUTE_PATH } from "../../../modules/task/routes";

interface AppErrorViewProps {
  title: string;
  message?: string;
  showHomeBtn?: boolean;
}

const AppErrorView: React.FC<AppErrorViewProps> = ({
  title,
  message,
  showHomeBtn,
}) => {
  const { t } = useTranslation();

  const handleGoHome = () => {
    window.location.href = ROOT_ROUTE_PATH;
  };

  return (
    <div className="app-error-view-container">
      <h2 className="app-error-view-title">{title}</h2>
      {message && <p className="app-error-view-text">{message}</p>}
      {showHomeBtn && (
        <button onClick={handleGoHome} className="app-error-view-button">
          {t("error.goToHome")}
        </button>
      )}
    </div>
  );
};

export default AppErrorView;
