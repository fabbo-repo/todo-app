import React from "react";
import { useTranslation } from "react-i18next";
import AppErrorView from "../app-error-view/app-error-view";

const AppNotFoundView: React.FC = () => {
  const { t } = useTranslation();

  return <AppErrorView title={t("error.pageNotFound")} showHomeBtn={true} />;
};

export default AppNotFoundView;
