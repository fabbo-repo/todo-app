import { Route, Routes } from "react-router-dom";
import React from "react";
import LoginView from "./components/login-view/login-view";
import RegisterView from "./components/register-view/register-view";

const AUTH_ROUTE_PATH = "/auth";
const LOGIN_ROUTE_SUBPATH = "/login";
const REGISTER_ROUTE_SUBPATH = "/register";

export const AUTH_BASE_ROUTE_PATH = AUTH_ROUTE_PATH + "/*";
export const LOGIN_ROUTE_PATH = AUTH_ROUTE_PATH + LOGIN_ROUTE_SUBPATH;
export const REGISTER_ROUTE_PATH = AUTH_ROUTE_PATH + REGISTER_ROUTE_SUBPATH;

export const AuthRoutes: React.FC = () => {
  return (
    <Routes>
      <Route path={LOGIN_ROUTE_SUBPATH} element={<LoginView />} />
      <Route path={REGISTER_ROUTE_SUBPATH} element={<RegisterView />} />
    </Routes>
  );
};
