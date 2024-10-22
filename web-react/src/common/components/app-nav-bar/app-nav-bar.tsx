import React, { useState, useRef, useEffect, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ChevronDown } from "lucide-react";
import { useTranslation } from "react-i18next";
import { ROOT_ROUTE_PATH } from "../../../modules/task/routes";
import { ACCOUNT_ROUTE_PATH } from "../../../modules/account/routes";
import { AccountContext } from "../../../modules/account/states/contexts/account-context";
import { logout } from "../../../modules/auth/usecases/logout-usecase";
import { Either } from "../../data/either";
import { AppError } from "../../data/errors/app-error";
import "./app-nav-bar.css";

const AppNavBar: React.FC = () => {
  const { t } = useTranslation();

  const { account } = useContext(AccountContext);

  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout().then((result: Either<AppError, void>) => {
      if (result.isRight()) {
        setDropdownOpen(false);
        navigate(ROOT_ROUTE_PATH);
      }
    });
  };

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target as Node)
      ) {
        setDropdownOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <nav className="app-nav-bar">
      <div className="app-nav-bar-container">
        <Link to={ROOT_ROUTE_PATH} className="app-nav-bar-brand">
          {t("navBar.title")}
        </Link>
        {account && (
          <div className="app-nav-bar-user" ref={dropdownRef}>
            <button
              onClick={() => setDropdownOpen(!dropdownOpen)}
              className="app-nav-bar-user-button"
            >
              <img
                src={account.imageUrl}
                alt={account.username}
                className="app-nav-bar-user-image"
              />
              <span>{account.username}</span>
              <ChevronDown size={20} />
            </button>
            {dropdownOpen && (
              <div className="app-nav-bar-dropdown">
                <Link
                  to={ACCOUNT_ROUTE_PATH}
                  onClick={() => setDropdownOpen(false)}
                >
                  {t("navBar.viewAccount")}
                </Link>
                <button onClick={handleLogout}>{t("auth.logout")}</button>
              </div>
            )}
          </div>
        )}
      </div>
    </nav>
  );
};

export default AppNavBar;
