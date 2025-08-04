FROM tomcat:9.0.46-jdk8-adoptopenjdk-openj9


RUN groupadd -r tomcat && useradd -r -g tomcat -d /usr/local/tomcat tomcat


RUN chown -R tomcat:tomcat $CATALINA_HOME \
        && chmod -R 755 $CATALINA_HOME \
        && chmod 400 $CATALINA_HOME/conf/* \
        && chmod -R 300 $CATALINA_HOME/logs \
        && chmod -R 770 $CATALINA_HOME/temp
USER tomcat

    # Copy the custom tomcat-users.xml into the container
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

    # Expose the default Tomcat port
EXPOSE 8080

    # Command to run Tomcat
CMD ["catalina.sh", "run"]