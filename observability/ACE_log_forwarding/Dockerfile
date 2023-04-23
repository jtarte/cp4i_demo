FROM cp.icr.io/cp/appc/ace-server-prod@sha256:f9b2b5e385f462d60a3fedf2aa7366b3bc304e971c89fafe6425cf2949a472c6

LABEL "ACE.version"="12.0.3-r1"

USER root
COPY logpoc.bar /home/aceuser/initial-config/bars/logpoc.bar
COPY Log4jLoggingNode_v2.0.0.par /opt/ibm/ace-12/server/jplugin
#COPY logger.xml /var/mqsi/common/wsrr/logger.xml
RUN  chmod -R ugo+rwx /home/aceuser

USER 1000
