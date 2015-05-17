Android Build with gradle, desktop build with ant





gradlew :ABattle.Desktop:run -x lint
gradlew run -x lint
gradlew build  -x lint
gradlew installDebug run -x lint

make android build
gradlew ABattle.Android:assembleRelease -x lintVitalRelease

