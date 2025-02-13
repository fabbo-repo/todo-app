# Todo App

## Overview

The `todo-app` is a full-stack application that allows users to manage their tasks efficiently. It consists of a backend API built with Spring Boot and a frontend web application built with React.

## Helm

The project uses Helm for managing Kubernetes deployments. The Helm charts are located in the `helm/` directory of both the backend and frontend.

### Key Files

- `api-spring-boot-java/helm/`: Contains Helm charts for deploying the backend.
- `web-react/helm/`: Contains Helm charts for deploying the frontend.

## Backend (api-spring-boot-java)

The backend is a Spring Boot application that provides RESTful APIs for managing tasks. It connects to a PostgreSQL database, S3 for image storage and uses Firebase for authentication.

[Useful Documentation](api-spring-boot-java/README.md)

## Frontend (web-react)

The frontend is a React application that provides a user interface for managing tasks. It communicates with the backend API and uses Firebase for authentication.

[Useful Documentation](web-react/README.md)

## Development Environment

The infrastructure directory contains configuration files for setting up the development environment, including Docker Compose and SonarQube.

### Key Files

- `docker-compose.yml`: Docker Compose configuration for setting up the development environment.
- `sonarqube/`: Contains configuration for SonarQube.

## CI/CD

The project uses GitHub Actions for continuous integration and deployment. The workflows are defined in the `.github/workflows/` directory.

### Key Workflows

- `api-spring-boot-java-main.yml`: Workflow for building and deploying the backend on the main branch.
- `api-spring-boot-java-develop.yml`: Workflow for building and testing the backend on the develop branch.
- `web-react-main.yml`: Workflow for building and deploying the frontend on the main branch.
- `web-react-develop.yml`: Workflow for building and testing the frontend on the develop branch.

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
