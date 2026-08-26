pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }

    environment {
        APP_JAR        = 'target/quiz-bg-1.0.0.jar'
        APPZ_HOME      = 'D:/tom/apache-tomcat-9.0.53'
        APPZ_ARTIFACTS = 'D:/MONTH-2/Week-4/wednesday/appzillon-artifacts'
        JAVA_HOME      = 'D:/software/jdk-21.0.8'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Backend Jar') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Deploy Backend (port 8080)') {
            steps {
                bat '''
                    for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr LISTENING') do (
                        taskkill /F /PID %%a
                        exit /b 0
                    )
                    exit /b 0
                '''
                bat 'set JENKINS_NODE_COOKIE=dontKillMe && start /B cmd /c "java -jar %APP_JAR% > backend.log 2>&1"'
            }
        }

        stage('Backend Health Check') {
            steps {
                bat '''
                    set RETRIES=15
                    :waitloop1
                    timeout /t 4 /nobreak >nul
                    curl -s -o nul -w "%%{http_code}" http://localhost:8080/api/user/getQuizzes | findstr 200
                    if errorlevel 1 (
                        set /a RETRIES-=1
                        if %RETRIES% GTR 0 goto waitloop1
                        exit /b 1
                    )
                    echo BACKEND UP ON 8080
                '''
            }
        }

        stage('Deploy Appzillon (Tomcat 8090)') {
            steps {
                bat '''
                    for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8090 ^| findstr LISTENING') do (
                        taskkill /F /PID %%a
                        exit /b 0
                    )
                    exit /b 0
                '''
                bat '''
                    timeout /t 3 /nobreak >nul
                    rmdir /S /Q "%APPZ_HOME%\\webapps\\AppzillonServer"
                    rmdir /S /Q "%APPZ_HOME%\\webapps\\quizzz"
                    del  /F /Q "%APPZ_HOME%\\webapps\\AppzillonServer.war"
                    del  /F /Q "%APPZ_HOME%\\webapps\\quizzz.war"
                    copy /Y "%APPZ_ARTIFACTS%\\AppzillonServer.war" "%APPZ_HOME%\\webapps\\"
                    copy /Y "%APPZ_ARTIFACTS%\\quizzz.war"       "%APPZ_HOME%\\webapps\\"
                    echo WARS DEPLOYED TO WEBAPPS
                '''
                bat 'set JENKINS_NODE_COOKIE=dontKillMe && start /B cmd /c "call %APPZ_HOME%/bin/catalina.bat run > %APPZ_HOME%/logs/jenkins-run.log 2>&1"'
            }
        }

        stage('Appzillon Health Check') {
            steps {
                bat '''
                    set RETRIES=30
                    :waitloop2
                    timeout /t 6 /nobreak >nul
                    curl -s -o nul -w "%%{http_code}" http://localhost:8090/quizzz/ | findstr 200
                    if errorlevel 1 (
                        set /a RETRIES-=1
                        if %RETRIES% GTR 0 goto waitloop2
                        exit /b 1
                    )
                    echo APPZILLON UP ON 8090
                '''
            }
        }
    }

    post {
        success {
            echo 'FULL STACK DEPLOYED: Backend 8080 + Appzillon 8090'
        }
        failure {
            echo 'Pipeline failed - check console output and app logs'
        }
    }
}
