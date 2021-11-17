pipeline {
    agent {
        kubernetes {
            yamlFile 'AgentPod.yaml'
            defaultContainer 'jnlp'
        }
    }
    
    stages {
        stage('git') {
            steps {
                git 'https://github.com/wslee950920/tennis-mauel-user-api.git'
            }
        }

        stage('Unit Test a Gradle project') {        
            //병렬로 수행해보려 하였으나 알 수 없는 이유로 계속 실패

                    steps {
                        container('gradle') {
                            sh 'gradle test'
                        }
                    }
        }

        stage('Integration Test a Gradle project') {        
            //병렬로 수행해보려 하였으나 알 수 없는 이유로 계속 실패

                    //추후 통합 테스트로 변경
                    steps {
                        container('gradle') {
                            sh 'gradle test'
                        }
                    }
        }

        stage('Test Results') {
            steps {
                junit '**/build/test-results/test/*.xml'
            }
        }

        stage('Build a Gradle project') {
            steps {
                container('gradle') {
                    sh 'gradle build -x test'
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