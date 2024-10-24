import React, { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { ACCOUNT_ROUTE_PATH } from "../../routes";
import { useTranslation } from "react-i18next";
import { AccountContext } from "../../states/contexts/account-context";
import AppTextField from "../../../../common/components/app-text-field/app-text-field";
import { updateAccount } from "../../usecases/update-account-usecase";
import ImageContainer from "../../../../common/components/image-container/image-container";
import AppTextButton from "../../../../common/components/app-text-button/app-text-button";
import "./account-edit-view.css";
import AppErrorView from "../../../../common/components/app-error-view/app-error-view";
import AppLoaderView from "../../../../common/components/app-loader-view/app-loader-view";
import { useAccountForm } from "../../hooks/use-account-form";
import AppTextArea from "../../../../common/components/app-text-area/app-text-area";
import {
  ACCOUNT_DESCRIPTION_MAX_CHARACTERS,
  ACCOUNT_USERNAME_MAX_CHARACTERS,
} from "../../data/constants/account-limit-constants";
import AppBackButton from "../../../../common/components/app-back-button/app-back-button";
import AppLocalePicker from "../../../../common/components/app-locale-picker/app-locale-picker";
import imageCompression from "browser-image-compression";
import AppLoaderBox from "../../../../common/components/app-loader-box/app-loader-box";

const AccountEditView: React.FC = () => {
  const { i18n, t } = useTranslation();

  const { account, setAccount } = useContext(AccountContext);

  const navigate = useNavigate();

  // Form hooks
  const [
    username,
    description,
    usernameError,
    descriptionError,
    handleUsernameChange,
    handleDescriptionChange,
    isFormValid,
  ] = useAccountForm();
  const [locale, setLocale] = useState("");
  const [image, setImage] = useState<File | undefined>(undefined);
  const [imageError, setImageError] = useState("");

  const [isImageLoading, setIsImageLoading] = useState<boolean>(false);

  const [previewImageUrl, setPreviewImageUrl] = useState(account?.imageUrl);

  const [isLoading, setIsLoading] = useState<boolean>(false);

  const [formError, setFormError] = useState("");

  useEffect(() => {
    if (account) {
      handleUsernameChange(account.username ?? "");
      handleDescriptionChange(account.description ?? "");
      setLocale(account.locale ?? i18n.language);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [account]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!isFormValid()) {
      return;
    }

    setIsLoading(true);

    updateAccount({
      username: username,
      description: description,
      image: image,
      locale: locale,
    }).then((result) => {
      result.fold(
        () => {
          setFormError(t("task.editView.genericError"));
          setIsLoading(false);
        },
        (account) => {
          setIsLoading(false);

          // Account data update
          setAccount(account);

          navigate(ACCOUNT_ROUTE_PATH);
        }
      );
    });
  };

  const handleImageChange = (newImage: File) => {
    setIsImageLoading(true);

    imageCompression(newImage, {
      maxSizeMB: 0.7,
      useWebWorker: true,
    }).then((compressedImage) => {
      if (compressedImage.size / 1000 > 700) {
        setImageError(t("account.imageMaxError"));
        return;
      }

      const reader = new FileReader();
      reader.onloadend = () => {
        setPreviewImageUrl(reader.result as string);
        setIsImageLoading(false);
      };
      reader.readAsDataURL(compressedImage);
      setImage(compressedImage);
    });
  };

  if (!account) {
    return (
      <AppErrorView
        title={t("account.loadingErrorTitle")}
        message={t("account.loadingErrorTitle")}
      />
    );
  }

  if (isLoading) {
    return <AppLoaderView />;
  }

  return (
    <div className="content">
      <div className="account-edit-view">
        <AppBackButton onClick={() => navigate(ACCOUNT_ROUTE_PATH)} />
        <h2>{t("account.editView.viewTitle")}</h2>
        <form onSubmit={handleSubmit}>
          <div className="account-edit-view-form-group">
            <label>{t("account.editView.imageLabel")}</label>
            {!isImageLoading && (
              <ImageContainer
                initialImageUrl={previewImageUrl!}
                onImageChange={handleImageChange}
                errorText={imageError}
              />
            )}
            {isImageLoading && <AppLoaderBox />}
          </div>
          <div className="account-edit-view-form-group">
            <label htmlFor="username">
              {t("account.editView.usernameLabel")}
            </label>
            <AppTextField
              id="username"
              text={username}
              onTextChange={handleUsernameChange}
              maxLength={ACCOUNT_USERNAME_MAX_CHARACTERS}
              errorText={usernameError}
            />
          </div>
          <div className="account-edit-view-form-group">
            <label htmlFor="locale">{t("account.localeTitle")}</label>
            <AppLocalePicker
              id="locale"
              locale={locale}
              onLocaleChange={(newLocale) => {
                setLocale(newLocale);
                i18n.changeLanguage(newLocale);
              }}
            />
          </div>
          <div className="account-edit-view-form-group">
            <label htmlFor="description">
              {t("account.editView.descriptionLabel")}
            </label>
            <AppTextArea
              id="description"
              text={description}
              onTextChange={handleDescriptionChange}
              maxLength={ACCOUNT_DESCRIPTION_MAX_CHARACTERS}
              errorText={descriptionError}
            />
          </div>
          {formError && <p className="error">{formError}</p>}
          <AppTextButton
            text={t("account.editView.saveChangesBtn")}
            isSubmit={true}
          />
        </form>
      </div>
    </div>
  );
};

export default AccountEditView;
