import React, { useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import AppTextButton from "../../../../common/components/app-text-button/app-text-button";
import { useTranslation } from "react-i18next";
import { REGISTER_ROUTE_PATH } from "../../routes";
import { loginWithEmail } from "../../usecases/login-with-email-usecase";
import { ROOT_ROUTE_PATH } from "../../../task/routes";
import AppTextField from "../../../../common/components/app-text-field/app-text-field";
import "./login-view.css";
import { LoginError, LoginErrorTypeEnum } from "../../data/errors/login-error";
import AppConfirmationDialog from "../../../../common/components/app-confirmation-dialog/app-confirmation-dialog";
import { sendEmailVerificationByEmail } from "../../usecases/send-email-verification-usecase";
import { logout } from "../../usecases/logout-usecase";
import { useLoginForm } from "../../hooks/use-login-form";

export const LOGIN_REDIRECT_QUERY_PARAM = "redirect";

const LoginView: React.FC = () => {
  const { t } = useTranslation();

  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const [isEmailVerificationDialogOpen, setIsEmailVerificationDialogOpen] =
    useState<boolean>(false);

  // Form hooks
  const [
    email,
    password,
    emailError,
    passwordError,
    handleEmailChange,
    handlePasswordChange,
    isFormValid,
  ] = useLoginForm();

  const [formError, setFormError] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!isFormValid()) {
      return;
    }

    loginWithEmail(email, password).then((result) => {
      result.fold(
        (error: LoginError) => {
          if (error.type === LoginErrorTypeEnum.CREDENTIALS_ERROR) {
            setFormError(t("auth.loginView.wrongCredentialsError"));
          } else if (
            error.type === LoginErrorTypeEnum.EMAIL_NOT_VERIFIED_ERROR
          ) {
            setIsEmailVerificationDialogOpen(true);
          } else {
            setFormError(t("auth.loginView.genericError"));
          }
        },
        () => {
          const redirectTo = searchParams.get(LOGIN_REDIRECT_QUERY_PARAM);
          navigate(redirectTo ?? ROOT_ROUTE_PATH);
        }
      );
    });
  };

  return (
    <div className="login-view-container">
      <div className="login-view-form">
        <h2 className="login-view-title">{t("auth.loginView.loginBtn")}</h2>
        <form onSubmit={handleSubmit}>
          <div className="login-view-form-group">
            <label htmlFor="email">{t("auth.emailLabel")}</label>
            <AppTextField
              id="email"
              text={email}
              errorText={emailError}
              onTextChange={(e) => handleEmailChange(e)}
            />
          </div>
          <div className="login-view-form-group">
            <label htmlFor="password">{t("auth.passwordLabel")}</label>
            <AppTextField
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
        <AppConfirmationDialog
          isOpen={isEmailVerificationDialogOpen}
          title={t("auth.loginView.emailVerificationDialogTitle")}
          content={t("auth.loginView.emailVerificationDialogContent")}
          onConfirm={() => {
            sendEmailVerificationByEmail();
            setIsEmailVerificationDialogOpen(false);
          }}
          onClose={() => {
            logout();
            setIsEmailVerificationDialogOpen(false);
          }}
        />
      </div>
    </div>
  );
};

export default LoginView;
