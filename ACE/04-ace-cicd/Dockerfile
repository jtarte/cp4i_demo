FROM cp.icr.io/cp/appc/ace-server-prod@sha256:084cbd25b8af37c841f0b8d1ffc401e2805018baeb4ef6997a2ce9c1c97a1441

LABEL "ACE.version"="12.0.5.0-r3"


USER root
COPY ACE/04-ace-cicd/mqsample.bar /home/aceuser/initial-config/bars/
RUN  chmod -R ugo+rwx /home/aceuser

USER 1000