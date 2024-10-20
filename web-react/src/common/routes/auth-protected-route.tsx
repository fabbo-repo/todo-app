import React from "react";
import { useLocation } from "react-router";
import { Navigate } from "react-router-dom";
import { LOGIN_ROUTE_PATH } from "../../modules/auth/routes";
import { sessionInstance } from "../services/instances";
import { ROOT_ROUTE_PATH } from "../../modules/task/routes";
import { LOGIN_REDIRECT_QUERY_PARAM } from "../../modules/auth/components/login-view/login-view";

interface AuthProtectedRouteProps {
  children: JSX.Element;
}

export const AuthProtectedRoute: React.FC<AuthProtectedRouteProps> = ({
  children,
}) => {
  const prevLocation = useLocation();
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
  }

  return children;
};
