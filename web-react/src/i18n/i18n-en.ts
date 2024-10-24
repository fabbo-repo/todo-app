export const enTranslations = {
  translation: {
    generic: {
      confirm: "Confirm",
      cancel: "Cancel",
    },
    error: {
      pageNotFound: "Página no encontrada",
      goToHome: "Ir a la página principal",
    },
    navBar: {
      title: "Todo App",
      viewAccount: "View Account",
    },
    locale: {
      english: "English",
      spanish: "Spanish",
    },
    auth: {
      emailLabel: "Email",
      passwordLabel: "Password",
      confirmPasswordLabel: "Confirm password",
      emailRequiredError: "Email is required",
      emailInvalidError: "Invalid email",
      passwordRequiredError: "Password is required",
      loginView: {
        title: "Login",
        loginBtn: "Login",
        registerLink: "Don't have an account? Register",
        genericError: "Could not login, something went wrong",
        wrongCredentialsError: "Wrong credentials",
        emailVerificationDialogTitle: "E-mail verification",
        emailVerificationDialogContent:
          "An email will be sent to verify your account, do you wish to continue?",
      },
      registerView: {
        title: "Create an account",
        registerBtn: "Register",
        loginLink: "Already have an account? Sign in",
        passwordMatchError: "Passwords do not match",
        weakPasswordError: "Weak password",
        registerError: "Account could not be created",
      },
      logout: "Logout",
    },
    account: {
      loadingErrorTitle: "Loading error",
      loadingErrorMessage: "Account data could not be loaded",
      localeTitle: "Language",
      view: {
        viewTitle: "Account Data",
      },
      editView: {
        viewTitle: "Account Edit",
        usernameLabel: "Username",
        descriptionLabel: "Description",
        imageLabel: "Image",
        saveChangesBtn: "Save changes",
      },
      usernameRequiredError: "The username is required",
      usernameMaxError: "The username has a maximum of 20 characters",
      descriptionMaxError: "The description has a maximum of 300 characters",
      imageMaxError: "The image should be less than 700 KB",
    },
    task: {
      view: {
        stateTitle: "State",
        createdAtTitle: "Date of creation",
        completedContent: "Completed",
        pendingContent: "Pending",
      },
      listView: {
        noTasks: "There are no pending tasks. Add a new one!",
      },
      editView: {
        viewTitle: "Edit Task",
        completedContent: "Completed",
        editBtn: "Update Task",
        genericError: "Could not update task, something went wrong",
      },
      createView: {
        createBtn: "Add Task",
        genericError: "Could not add task, something went wrong",
      },
      titleTitle: "Title",
      descriptionTitle: "Description",
      deadlineTitle: "Deadline",
      taskLoadError: "Task not found",
      deleteTaskDialogTitle: "Confirm deletion",
      deleteTaskDialogContent: "Are you sure you want to eliminate this task?",
      titleRequiredError: "The title is required",
      titleMaxError: "The title has a maximum of 50 characters",
      descriptionMaxError: "The description has a maximum of 300 characters",
      deadlineFutureError: "The deadline must be in the future",
    },
  },
};
