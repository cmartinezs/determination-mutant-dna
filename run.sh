#!/bin/sh
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DSPRING_PROFILE=local -DAPP_VERSION=1.0.0-M1"