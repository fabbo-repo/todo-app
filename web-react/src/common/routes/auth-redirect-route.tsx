import React, { useState } from "react";
import { Navigate } from "react-router-dom";
import { sessionInstance } from "../services/instances";
import { ROOT_ROUTE_PATH } from "../../modules/task/routes";
import AppLoaderView from "../components/app-loader-view/app-loader-view";
import { firebaseAuth } from "../config/firebase";

interface AuthRedirectRouteProps {
  children: JSX.Element;
}

export const AuthRedirectRoute: React.FC<AuthRedirectRouteProps> = ({
  children,
}) => {
  const [isAuthReady, setIsAuthReady] = useState<boolean>(false);

  firebaseAuth.authStateReady().then(() => {
    setIsAuthReady(true);
  });

  if (!isAuthReady) {
    return <AppLoaderView />;
  }

  if (sessionInstance.isLoggedIn) {
    return <Navigate to={ROOT_ROUTE_PATH} replace />;
  }

  return children;
};
