# See here for image contents: https://github.com/microsoft/vscode-dev-containers/tree/v0.205.2/containers/java/.devcontainer/base.Dockerfile

# [Choice] Java version: 11, 14, 16, 17
ARG VARIANT="17"
FROM mcr.microsoft.com/vscode/devcontainers/java:${VARIANT}

# [Optional] Install Maven
ARG INSTALL_MAVEN="true"
RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "source /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven"; fi

