pipeline {
    agent {
        kubernetes {
            label 'tennis-mauel'
            yamlFile 'JenkinsPod.yaml'
        }
    }
    
    stages {
        stage('Test&Build') {
            git 'https://github.com/wslee950920/tennis-mauel-user-api.git'
        
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