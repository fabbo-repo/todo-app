import React from "react";
import { useLocation, useNavigate } from "react-router";
import { Navigate } from "react-router-dom";
import { LOGIN_ROUTE_PATH } from "../../modules/auth/routes";
import { sessionInstance } from "../services/instances";
import { ROOT_ROUTE_PATH } from "../../modules/task/routes";
import { LOGIN_REDIRECT_QUERY_PARAM } from "../../modules/auth/components/login-view/login-view";
import { firebaseAuth } from "../config/firebase";
import AppConfirmationDialog from "../components/app-confirmation-dialog/app-confirmation-dialog";
import { useTranslation } from "react-i18next";
import { sendEmailVerificationByEmail } from "../../modules/auth/usecases/send-email-verification-usecase";
import { logout } from "../../modules/auth/usecases/logout-usecase";

interface AuthProtectedRouteProps {
  children: JSX.Element;
}

export const AuthProtectedRoute: React.FC<AuthProtectedRouteProps> = ({
  children,
}) => {
  const { t } = useTranslation();

  const prevLocation = useLocation();
  const navigate = useNavigate();

  if (
    !sessionInstance.isLoggedIn &&
    prevLocation.pathname !== LOGIN_ROUTE_PATH
  ) {
    if (prevLocation.pathname === ROOT_ROUTE_PATH) {
      return <Navigate to={LOGIN_ROUTE_PATH} replace />;
    }
    return (
      <Navigate
        to={`${LOGIN_ROUTE_PATH}?${LOGIN_REDIRECT_QUERY_PARAM}=${prevLocation.pathname}`}
        replace
      />
    );
  } else if (
    sessionInstance.isLoggedIn &&
    !firebaseAuth.currentUser?.emailVerified
  ) {
    return (
      <AppConfirmationDialog
        isOpen={true}
        title={t("auth.loginView.emailVerificationDialogTitle")}
        content={t("auth.loginView.emailVerificationDialogContent")}
        onConfirm={() => {
          sendEmailVerificationByEmail();
          navigate(LOGIN_ROUTE_PATH);
        }}
        onClose={() => {
          logout();
          navigate(LOGIN_ROUTE_PATH);
        }}
      />
    );
  }

  return children;
};
