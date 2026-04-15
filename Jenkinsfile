// Jenkinsfile for First Non-Repeating Character Finder
// Pipeline as Code - Declarative approach

pipeline {
    agent any

    environment {
        PROJECT_NAME = 'first-non-repeating-char'
        BUILD_ARTIFACTS = 'target'
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_LOGIN = 'admin'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
        timestamps()
    }

    triggers {
        pollSCM('H/15 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                echo '========== Checking out source code =========='
                checkout scm
                bat 'git log --oneline -5'
            }
        }

        stage('Build') {
            steps {
                echo '========== Building Maven project =========='
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo '========== Running unit tests (16 tests) =========='
                bat 'mvn test'
            }
        }

        stage('Code Coverage') {
            steps {
                echo '========== Running JaCoCo code coverage =========='
                bat 'mvn jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo '========== Running SonarQube code quality analysis =========='
                script {
                    try {
                        bat '''
                            mvn sonar:sonar ^
                                -Dsonar.host.url=%SONAR_HOST_URL% ^
                                -Dsonar.login=%SONAR_LOGIN% ^
                                -Dsonar.projectKey=%PROJECT_NAME% ^
                                -Dsonar.projectName=%PROJECT_NAME% ^
                                -Dsonar.sources=src/main ^
                                -Dsonar.tests=src/test ^
                                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        '''
                        echo '✓ SonarQube analysis completed successfully'
                    } catch (Exception e) {
                        echo '⚠ WARNING: SonarQube server is not available'
                        echo "  Error: ${e.message}"
                        echo "  To start SonarQube, use: docker run -d -p 9000:9000 sonarqube:latest"
                        echo "  Pipeline will continue without code quality metrics"
                    }
                }
            }
        }

        stage('Package') {
            steps {
                echo '========== Packaging JAR artifact =========='
                bat 'mvn package -DskipTests'
            }
        }

        stage('Demo - Run Application') {
            steps {
                echo '========== Running application to demonstrate output =========='
                bat 'java -jar target/first-non-repeating-char-1.0.0.jar'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo '========== Archiving build artifacts =========='
                archiveArtifacts artifacts: 'target/*.jar,target/site/jacoco/**', 
                                 allowEmptyArchive: false
            }
        }
    }

    post {
        always {
            echo '========== Publishing test results =========='
            junit 'target/surefire-reports/*.xml'
        }

        success {
            echo "✓ Build SUCCESS - Build #${BUILD_NUMBER} completed successfully"
            echo "✓ SonarQube analysis available at: ${SONAR_HOST_URL}/projects"
            emailext(
                subject: "Build SUCCESS: ${PROJECT_NAME} #${BUILD_NUMBER}",
                body: """
                Project: ${PROJECT_NAME}
                Build Number: ${BUILD_NUMBER}
                Build URL: ${BUILD_URL}
                Status: SUCCESS
                
                SonarQube Dashboard: ${SONAR_HOST_URL}/projects
                Artifacts: ${BUILD_URL}artifact/
                Test Report: ${BUILD_URL}testReport/
                
                Code Quality Metrics available in SonarQube!
                """,
                recipientProviders: [developers(), requestor()]
            )
        }

        failure {
            echo "✗ Build FAILED - Build #${BUILD_NUMBER} failed"
            emailext(
                subject: "Build FAILED: ${PROJECT_NAME} #${BUILD_NUMBER}",
                body: """
                Project: ${PROJECT_NAME}
                Build Number: ${BUILD_NUMBER}
                Build URL: ${BUILD_URL}
                Status: FAILED
                
                Console Output: ${BUILD_URL}console
                Check SonarQube for detailed analysis!
                """,
                recipientProviders: [developers(), requestor(), brokenBuildSuspects()]
            )
        }

        unstable {
            echo "⚠ Build UNSTABLE - Build #${BUILD_NUMBER} is unstable"
        }

        cleanup {
            echo '========== Cleanup =========='
            deleteDir()
            echo 'Workspace cleaned up'
        }
    }
}
