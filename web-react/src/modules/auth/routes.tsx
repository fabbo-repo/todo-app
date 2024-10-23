import { Route } from "react-router-dom";
import LoginView from "./components/login-view/login-view";
import RegisterView from "./components/register-view/register-view";

export const LOGIN_ROUTE_PATH = "/login";
export const REGISTER_ROUTE_PATH = "/register";

export const authRoutes = () => {
  return (
    <>
      <Route path={LOGIN_ROUTE_PATH} element={<LoginView />} />
      <Route path={REGISTER_ROUTE_PATH} element={<RegisterView />} />
    </>
  );
};
