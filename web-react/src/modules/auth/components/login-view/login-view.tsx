import React, { useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import AppTextButton from "../../../../common/components/app-text-button/app-text-button";
import { useTranslation } from "react-i18next";
import { REGISTER_ROUTE_PATH } from "../../routes";
import { loginWithEmail } from "../../usecases/login-with-email-usecase";
import { ROOT_ROUTE_PATH } from "../../../task/routes";
import AppTextInput from "../../../../common/components/app-text-field/app-text-field";
import "./login-view.css";
import { useLoginEmailForm } from "../../hooks/use-login-email-form";
import { useLoginPasswordForm } from "../../hooks/use-login-password-form";

export const LOGIN_REDIRECT_QUERY_PARAM = "redirect";

const LoginView: React.FC = () => {
  const { t } = useTranslation();

  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  // Form hooks
  const [email, emailError, handleEmailChange, isEmailValid] =
    useLoginEmailForm();
  const [password, passwordError, handlePasswordChange, isPasswordValid] =
    useLoginPasswordForm();

  const [formError, setFormError] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    // Check if email is valid
    if (!isEmailValid() && !emailError) {
      handleEmailChange(email);
    }
    // Check if password is valid
    if (!isPasswordValid() && !passwordError) {
      handlePasswordChange(password);
    }
    if (!isEmailValid() || !isPasswordValid()) {
      return;
    }

    loginWithEmail(email, password).then(
      () => {
        setFormError(t("auth.loginView.wrongCredentialsError"));
      },
      () => {
        const redirectTo = searchParams.get(LOGIN_REDIRECT_QUERY_PARAM);
        navigate(redirectTo ?? ROOT_ROUTE_PATH);
      }
    );
  };

  return (
    <div className="login-view-container">
      <div className="login-view-form">
        <h2 className="login-view-title">{t("auth.loginView.loginBtn")}</h2>
        <form onSubmit={handleSubmit}>
          <div className="login-view-form-group">
            <label htmlFor="email">{t("auth.emailLabel")}</label>
            <AppTextInput
              id="email"
              text={email}
              errorText={emailError}
              onTextChange={(e) => handleEmailChange(e)}
            />
          </div>
          <div className="login-view-form-group">
            <label htmlFor="password">{t("auth.passwordLabel")}</label>
            <AppTextInput
              id="password"
              type="password"
              text={password}
              errorText={passwordError}
              onTextChange={(e) => handlePasswordChange(e)}
            />
          </div>
          {formError && <p className="error">{formError}</p>}
          <AppTextButton text={t("auth.loginView.loginBtn")} isSubmit={true} />
        </form>
        <Link to={REGISTER_ROUTE_PATH} className="login-view-register-link">
          {t("auth.loginView.registerLink")}
        </Link>
      </div>
    </div>
  );
};

export default LoginView;
