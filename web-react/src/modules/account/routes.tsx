import { Route } from "react-router-dom";
import AccountEditView from "./components/account-edit-view/account-edit-view";
import AccountView from "./components/account-view/account-view";

export const ACCOUNT_ROUTE_PATH = "/account";
export const ACCOUNT_EDIT_ROUTE_PATH = "/account/edit";

export const accountRoutes = () => {
  return (
    <>
      <Route path={ACCOUNT_ROUTE_PATH} element={<AccountView />} />
      <Route path={ACCOUNT_EDIT_ROUTE_PATH} element={<AccountEditView />} />
    </>
  );
};
