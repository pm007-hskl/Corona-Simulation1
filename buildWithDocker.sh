#!/bin/bash

docker run -it --rm --name pm007 -v "$(pwd)":/usr/src/pm007 -w /usr/src/pm007 maven mvn install:install-file -Dfile=src/lib/G4P.jar -DgroupId=G4P -DartifactId=G4P -Dversion=1.0 -Dpackaging=jar clean package
