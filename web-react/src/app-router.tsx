import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AUTH_BASE_ROUTE_PATH, AuthRoutes } from "./modules/auth/routes";
import AppNavBar from "./common/components/app-nav-bar/app-nav-bar";
import { TaskRoutes } from "./modules/task/routes";
import { AccountRoutes } from "./modules/account/routes";
import React from "react";
import { AuthProtectedRoute } from "./common/routes/auth-protected-route";
import AppNotFoundView from "./common/components/app-not-found-view/app-not-found-view";
import { AuthRedirectRoute } from "./common/routes/auth-redirect-route";
import { AccountProvider } from "./modules/account/states/providers/account-provider";

export const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path={AUTH_BASE_ROUTE_PATH}
          element={
            <AuthRedirectRoute>
              <AuthRoutes />
            </AuthRedirectRoute>
          }
        ></Route>
        <Route
          path="/*"
          element={
            <AuthProtectedRoute>
              <AccountProvider>
                <AppNavBar />
                <AccountRoutes />
                <TaskRoutes />
              </AccountProvider>
            </AuthProtectedRoute>
          }
        />
        <Route path="*" element={<AppNotFoundView />} />
      </Routes>
    </BrowserRouter>
  );
};
