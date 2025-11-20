pipeline {
    agent any

    tools {
        maven 'Maven-3.9.0'       // Make sure this matches your Jenkins Global Tool Config
        jdk 'JDK-17'              // Match the POM compiler release version
    }

    environment {
        // Optional: Headless browser display (useful for Linux agents with Xvfb)
        DISPLAY = ':99'
        // Browser configuration from parameters
        BROWSER = "${params.BROWSER ?: 'chrome'}"
        HEADLESS = "${params.HEADLESS ?: 'true'}"
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Browser to run tests on'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run tests in headless mode'
        )
        choice(
            name: 'TEST_SUITE',
            choices: ['all', 'smoke', 'regression'],
            description: 'Test suite to run'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Code checked out successfully'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                script {
                    def testCommand = 'mvn test'

                    // Branch-based test execution
                    if (env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'master') {
                        testCommand = 'mvn test -Dsuite=regression'
                    } else if (env.BRANCH_NAME.startsWith('feature/')) {
                        testCommand = 'mvn test -Dsuite=smoke'
                    }

                    echo "Running tests with command: ${testCommand}"
                    bat testCommand
                }
            }
            post {
                always {
                    // ✅ Publish TestNG results
                    junit '**/target/surefire-reports/*.xml'

                    // ✅ Archive test reports
                    archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true

                    // ✅ Publish HTML report if index.html exists
                    publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/surefire-reports',
                        reportFiles: 'index.html',
                        reportName: 'Test Report'
                    ])
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                bat 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
