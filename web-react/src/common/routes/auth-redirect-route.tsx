import React from "react";
import { Navigate } from "react-router-dom";
import { sessionInstance } from "../services/instances";
import { ROOT_ROUTE_PATH } from "../../modules/task/routes";

interface AuthRedirectRouteProps {
  children: JSX.Element;
}

export const AuthRedirectRoute: React.FC<AuthRedirectRouteProps> = ({
  children,
}) => {
  if (sessionInstance.isLoggedIn) {
    return <Navigate to={ROOT_ROUTE_PATH} replace />;
  }

  return children;
};
