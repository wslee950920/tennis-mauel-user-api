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

        stage('Test a Gradle project') {  
            //추후 통합 테스트를 병렬로 수행      
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
                    sh 'ls ./build/libs'
                }
            }
        }

        stage('Build a Docker image') {
            environment {
                tag = "${env.BUILD_ID}"
            }

            steps {
                container('docker') {
                    sh 'docker build -t tennis-mauel-user-api:$tag .'
                    sh 'docker image ls'
                }
            }
        }
    }
}