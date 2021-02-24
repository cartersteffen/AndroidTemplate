FROM jenkins/jenkins:lts
USER root
RUN apt-get -y update
RUN apt-get install git