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
            }
        }

        stage('Test&Build a Gradle project') {        
            parallel {
                stage('Unit Test') {
                    steps {
                        container('gradle') {
                            sh 'ls -a' 
                            sh './gradlew clean test'
                        }
                    }
                }
            }
        }

        stage('Build&Push a Docker image') {
            stage('Build') {
                steps {
                    container('docker') {
                        sh 'ls -a'
                    }
                }
            }
        }
    }
}