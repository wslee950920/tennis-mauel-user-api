pipeline {
    agent {
        kubernetes {
            yamlFile 'AgentPod.yaml'
        }
    }
    
    stages {
        stage('git') {
            steps {
                git 'https://github.com/wslee950920/tennis-mauel-user-api.git'
            }
        }

        stage('Test a Gradle project') {        
            parallel {
                stage('Unit Test') {
                    steps {
                        container('gradle1') {
                            sh './gradlew clean test'
                        }
                    }
                }

                //추후 통합 테스트를 병렬로 수행
                stage('Integration Test') {
                    steps {
                        container('gradle2') {
                            sh './gradlew clean test'
                            junit '**/build/test-results/test/*.xml'
                        }
                    }
                }
            }
        }

        stage('Build a Gradle project') {
            steps {
                container('gradle3') {
                    sh './gradlew clean build -x test'
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
                }
            }
        }
    }
}