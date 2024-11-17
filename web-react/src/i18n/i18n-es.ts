export const esTranslations = {
  translation: {
    generic: {
      confirm: "Confirmar",
      cancel: "Cancelar",
    },
    error: {
      pageNotFound: "Page not found",
      goToHome: "Go to home page",
    },
    navBar: {
      title: "Todo App",
      viewAccount: "Ver Cuenta",
    },
    locale: {
      english: "Inglés",
      spanish: "Español",
    },
    auth: {
      emailLabel: "Correo electrónico",
      passwordLabel: "Contraseña",
      confirmPasswordLabel: "Confirmar contraseña",
      emailRequiredError: "Correo electrónico es obligatorio",
      emailInvalidError: "Correo electrónico inválido",
      passwordRequiredError: "Contraseña es obligatoria",
      loginView: {
        title: "Iniciar sesión",
        loginBtn: "Iniciar sesión",
        registerLink: "¿No tienes una cuenta? Regístrate",
        genericError: "No se pudo iniciar sesión, algo fue mal",
        wrongCredentialsError: "Credenciales inválidas",
        emailVerificationDialogTitle: "Verificación de correo electrónico",
        emailVerificationDialogContent:
          "Se enviará un correo electrónico para verificar tu cuenta. ¿Deseas continuar?",
      },
      registerView: {
        title: "Crear una cuenta",
        registerBtn: "Registrarse",
        loginLink: "¿Ya tienes una cuenta? Inicia sesión",
        passwordMatchError: "Las contraseñas no coinciden",
        weakPasswordError: "Contraseña debil",
        registerError: "No se ha podido crear la cuenta",
      },
      logout: "Cerrar Sesión",
    },
    account: {
      loadingErrorTitle: "Error de carga",
      loadingErrorMessage: "No se han podido cargar los datos de la cuenta",
      localeTitle: "Idioma",
      view: {
        viewTitle: "Account Data",
      },
      editView: {
        usernameLabel: "Nombre de Usuario",
        descriptionLabel: "Descripción",
        imageLabel: "Imagen",
        saveChangesBtn: "Guardar cambios",
      },
      usernameRequiredError: "El nombre de usuario es obligatorio",
      usernameMaxError: "El nombre de usuario tiene un máximo de 20 caracteres",
      descriptionMaxError: "La descripción tiene un máximo de 300 caracteres",
      imageMaxError: "La imagen debe ser menor a 700 KB",
    },
    task: {
      view: {
        stateTitle: "Estado",
        createdAtTitle: "Fecha de creación",
        deadlineTitle: "Fecha límite",
        completedContent: "Completada",
        pendingContent: "Pendiente",
      },
      listView: {
        noTasks: "No hay tareas pendientes. ¡Añade una nueva!",
      },
      editView: {
        completedContent: "Completada",
        editBtn: "Actualizar Tarea",
        genericError: "No se pudo actualizar la tarea, algo fue mal",
      },
      createView: {
        createBtn: "Añadir Tarea",
        genericError: "No se pudo añadir la tarea, algo fue mal",
      },
      titleTitle: "Título",
      descriptionTitle: "Descripción",
      taskLoadError: "Tarea no encontrada",
      deleteTaskDialogTitle: "Confirmar eliminación",
      deleteTaskDialogContent: "¿Quieres eliminar esta tarea?",
      titleRequiredError: "El título es obligatorio",
      titleMaxError: "El título tiene un máximo de 50 caracteres",
      descriptionMaxError: "La descripción tiene un máximo de 300 caracteres",
      deadlineFutureError: "La fecha límite debe ser en el futuro",
    },
  },
};
