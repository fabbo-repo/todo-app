import React from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import "./app-not-found-view.css";
import { ROOT_ROUTE_PATH } from "../../../modules/task/routes";

const AppNotFoundView: React.FC = () => {
  const { t } = useTranslation();

  const navigate = useNavigate();

  const handleGoHome = () => {
    navigate(ROOT_ROUTE_PATH);
  };

  return (
    <div className="app-not-found-view-container">
      <h2 className="app-not-found-view-title">{t("error.pageNotFound")}</h2>
      <button onClick={handleGoHome} className="app-not-found-view-button">
        {t("error.goToHome")}
      </button>
    </div>
  );
};

export default AppNotFoundView;
