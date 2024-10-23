import React, { useEffect, useState } from "react";
import { Navigate, useSearchParams } from "react-router-dom";
import { sessionInstance } from "../services/instances";
import { ROOT_ROUTE_PATH } from "../../modules/task/routes";
import AppLoaderView from "../components/app-loader-view/app-loader-view";
import { firebaseAuth } from "../config/firebase";
import { LOGIN_REDIRECT_QUERY_PARAM } from "../../modules/auth/components/login-view/login-view";

interface AuthRedirectRouteProps {
  children: JSX.Element;
}

export const AuthRedirectRoute: React.FC<AuthRedirectRouteProps> = ({
  children,
}) => {
  const [searchParams] = useSearchParams();

  const [isAuthReady, setIsAuthReady] = useState<boolean>(false);

  const redirectTo = searchParams.get(LOGIN_REDIRECT_QUERY_PARAM);

  useEffect(() => {
    firebaseAuth.authStateReady().then(() => {
      setIsAuthReady(true);
    });
  }, []);

  if (!isAuthReady) {
    return <AppLoaderView />;
  }

  if (sessionInstance.isLoggedIn) {
    return <Navigate to={redirectTo ?? ROOT_ROUTE_PATH} replace />;
  }

  return children;
};
