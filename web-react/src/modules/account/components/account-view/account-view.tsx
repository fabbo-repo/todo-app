import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { Edit } from "lucide-react";
import { ACCOUNT_EDIT_ROUTE_PATH } from "../../routes";
import { AccountContext } from "../../states/contexts/account-context";
import AppErrorView from "../../../../common/components/app-error-view/app-error-view";
import { useTranslation } from "react-i18next";
import "./account-view.css";
import { localeToText } from "../../../../common/utils/form-utils";

const AccountView: React.FC = () => {
  const { i18n, t } = useTranslation();

  const { account } = useContext(AccountContext);

  if (!account) {
    return (
      <AppErrorView
        title={t("account.loadingErrorTitle")}
        message={t("account.loadingErrorTitle")}
      />
    );
  }

  return (
    <div className="content">
      <div className="account-view">
        <div className="account-view-header">
          <h2 className="account-view-title">{t("account.view.viewTitle")}</h2>
          <Link to={ACCOUNT_EDIT_ROUTE_PATH} className="account-view-edit-link">
            <Edit size={24} />
          </Link>
        </div>
        <div className="account-view-content">
          <img
            src={account.imageUrl}
            alt={account.username}
            className="account-view-image"
          />
          <div className="account-view-info">
            <h3>{account.username ?? "-"}</h3>
            <p>{account.description ?? ""}</p>
            <p>
              <strong>{t("account.localeTitle")}:</strong>{" "}
              {localeToText(account.locale ?? i18n.language, t)}
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AccountView;
