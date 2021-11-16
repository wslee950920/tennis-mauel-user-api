pipeline {
    agent {
        kubernetes {
            label 'tennis-mauel'
            yamlFile 'JenkinsPod.yaml'
        }
    }
    
    stages {
        stage('git') {
            steps {
                git 'https://github.com/wslee950920/tennis-mauel-user-api.git'
                sh 'ls -a'
            }
        }

        stage('Test a Gradle project') {        
            parallel {
                stage('Unit Test') {
                    steps {
                        container('gradle') {
                            sh './gradlew clean test'
                        }
                    }
                }
            }
        }

        stage('Build a Gradle project') {
            steps {
                container('gradle') {
                    sh './gradlew clean build -x test'
                }
            }
        }

        stage('Build a Docker image') {
            steps {
                container('docker') {
                    sh 'ls -a'
                }
            }
        }
    }
}