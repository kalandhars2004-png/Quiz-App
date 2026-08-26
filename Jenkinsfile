pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }

    environment {
        APP_JAR = 'target/quiz-bg-1.0.0.jar'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Stop Old Instance') {
            steps {
                bat '''
                    for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr LISTENING') do (
                        taskkill /F /PID %%a
                        exit /b 0
                    )
                    exit /b 0
                '''
            }
        }

        stage('Run App') {
            steps {
                bat 'set JENKINS_NODE_COOKIE=dontKillMe && start /B cmd /c "java -jar %APP_JAR% > app.log 2>&1"'
            }
        }

        stage('Health Check') {
            steps {
                bat '''
                    set RETRIES=10
                    :waitloop
                    timeout /t 6 /nobreak >nul
                    curl -s -o nul -w "%%{http_code}" http://localhost:8080/api/user/getQuizzes | findstr 200
                    if errorlevel 1 (
                        set /a RETRIES-=1
                        if %RETRIES% GTR 0 goto waitloop
                        exit /b 1
                    )
                    echo APP IS UP
                '''
            }
        }
    }

    post {
        success {
            echo 'Quiz App deployed and running on port 8080'
        }
        failure {
            echo 'Pipeline failed - check console output'
        }
    }
}
