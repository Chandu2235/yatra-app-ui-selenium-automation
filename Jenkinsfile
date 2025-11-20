pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.0' // Configure this name in Jenkins Global Tool Configuration
        jdk 'JDK-22' // Configure this name in Jenkins Global Tool Configuration
    }
    
    environment {
        // Set display for headless browser testing
        DISPLAY = ':99'
        // Browser configuration
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
                    // Publish TestNG results
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                    
                    // Archive test reports
                    archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
                    
                    // Generate HTML reports
                    publishHTML([
                        allowMissing: false,
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