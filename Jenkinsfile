pipeline {
    agent {
        kubernetes {
            yamlFile 'AgentPod.yaml'
            defaultContainer 'jnlp'
        }
    }
    parameters {
        string(name: 'REGISTRY', defaultValue: 'localhost:30125')
    }
    
    stages {
        stage('git') {
            steps {
                git 'https://github.com/wslee950920/tennis-mauel-user-api.git'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                container('gradle') {
                    withSonarQubeEnv('sonarqube') {
                        sh "gradle sonarqube"
                    }
                }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Test a Gradle project') {    
            parallel {
                stage('Unit Test') {
                    steps {
                        container('gradle') {
                            sh 'gradle test'
                        }
                    }
                }

                stage('Integration Test') {        
                    steps {
                        container('gradle2') {
                            //TODO: 추후 통합테스트로 변경
                            sh 'gradle test'
                        }
                    }
                }
            }    
        }

        stage('Build a Gradle project') {
            steps {
                container('gradle') {
                    sh 'gradle build -x test'
                    junit '**/build/test-results/test/*.xml'
                }
            }
        }

        stage('Build a Docker image') {
            steps {
                container('docker') {
                    sh "docker build -t tennis-mauel-user-api:${env.BUILD_ID} ."
                }
            }
        }

        stage('Push a Docker image') {
            steps {
                container('docker') {
                    sh "docker tag tennis-mauel-user-api:${env.BUILD_ID} ${params.REGISTRY}/tennis-mauel-user-api:${env.BUILD_ID}"
                    sh "docker push ${params.REGISTRY}/tennis-mauel-user-api:${env.BUILD_ID}"
                }
            }
        }
    }
}