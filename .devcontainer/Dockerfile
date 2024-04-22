# syntax=docker/dockerfile:1.4
FROM rubensa/ubuntu-tini-dev as base
# Install Java
ARG JAVA_VERSION=21.0
RUN <<EOT
#!/bin/bash -l
echo "# Installing Java..."
export SDKMAN_DIR=/opt/sdkman
. /opt/sdkman/bin/sdkman-init.sh
export JAVA_VERSION=${JAVA_VERSION}
export JAVA_INSTALL_VERSION=$(sdk list java | grep -o "${JAVA_VERSION}\.[0-9\.]*-tem" | head -1)
sdk install java $JAVA_INSTALL_VERSION || true
sudo mkdir -p /usr/lib/jvm
sudo ln -s /opt/sdkman/candidates/java/$JAVA_INSTALL_VERSION /usr/lib/jvm/$JAVA_VERSION
sdk default java $JAVA_INSTALL_VERSION
EOT
# Install Maven
ARG MAVEN_VERSION=3.9
RUN <<EOT
#!/bin/bash -l
echo "# Installing Maven..."
export SDKMAN_DIR=/opt/sdkman
. /opt/sdkman/bin/sdkman-init.sh
export MAVEN_VERSION=${MAVEN_VERSION}
export MAVEN_INSTALL_VERSION=$(sdk list maven | grep -o "${MAVEN_VERSION}\.[0-9\.]*" | head -1)
sdk install maven $MAVEN_INSTALL_VERSION || true
sdk default maven $MAVEN_INSTALL_VERSION
EOT

FROM base
# Keep container running (for use in VSCode)
CMD [ "tail", "-f", "/dev/null" ]
