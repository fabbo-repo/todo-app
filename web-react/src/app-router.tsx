import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import { authRoutes } from "./modules/auth/routes";
import { taskRoutes } from "./modules/task/routes";
import { accountRoutes } from "./modules/account/routes";
import React from "react";
import { AuthProtectedRoute } from "./common/routes/auth-protected-route";
import AppNotFoundView from "./common/components/app-not-found-view/app-not-found-view";
import { AuthRedirectRoute } from "./common/routes/auth-redirect-route";
import { AccountProvider } from "./modules/account/states/providers/account-provider";
import { TaskPageProvider } from "./modules/task/states/providers/task-page-provider";
import AppNavBar from "./common/components/app-nav-bar/app-nav-bar";

export const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          element={
            <AuthRedirectRoute>
              <Outlet />
            </AuthRedirectRoute>
          }
        >
          {authRoutes()}
        </Route>
        <Route
          element={
            <AuthProtectedRoute>
              <AccountProvider>
                <TaskPageProvider>
                  <AppNavBar />
                  <Outlet />
                </TaskPageProvider>
              </AccountProvider>
            </AuthProtectedRoute>
          }
        >
          {accountRoutes()}
          {taskRoutes()}
        </Route>
        <Route path="*" element={<AppNotFoundView />} />
      </Routes>
    </BrowserRouter>
  );
};
