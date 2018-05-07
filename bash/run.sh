#!/bin/sh

M2_HOME='/usr/share/maven'
export M2_HOME
mvn=${M2_HOME}/bin/mvn
cd ../
${mvn} clean install
cd target
java -jar jaxb-sheet.jar
