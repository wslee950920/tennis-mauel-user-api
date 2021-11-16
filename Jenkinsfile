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

        stage('Test&Build') {        
            parallel {
                stage('Unit Test') {
                    steps {
                        sh './gradlew clean test'
                        junit '**/build/test-results/test/*.xml'
                    }
                }
            }
        }
    }
}