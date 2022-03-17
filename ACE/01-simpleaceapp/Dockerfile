FROM cp.icr.io/cp/appc/ace-server-prod@sha256:f9b2b5e385f462d60a3fedf2aa7366b3bc304e971c89fafe6425cf2949a472c6

LABEL "ACE.version"="12.0.3-r1"


USER root
COPY SimpleACEApp.bar /home/aceuser/initial-config/bars/SimpleACEApp.bar
RUN  chmod -R ugo+rwx /home/aceuser

USER 1000
