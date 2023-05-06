FROM openjdk:11-slim

ARG USERNAME=tobiuo
ARG UID=1000
ARG GID=50 # staff

RUN apt-get update && \
    useradd -m $USERNAME -u $UID -g $GID 

WORKDIR /home/$USERNAME/
CMD ["/bin/bash"]
