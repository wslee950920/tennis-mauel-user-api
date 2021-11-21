pipeline {
    agent {
        kubernetes {
            yamlFile 'AgentPod.yaml'
            defaultContainer 'jnlp'
        }
    }
    options { skipDefaultCheckout(true) }
    environment {
        dockerImage=''
    }
    
    stages {
        stage('Start') {
            steps {
                slackSend (channel: '#jenkins', color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
            }
        }

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
                    script {
                        dockerImage = docker.build('tennis-mauel-user-api:$BUILD_ID')
                    }
                }
            }
        }

        stage('Push a Docker image') {
            steps {
                container('docker') {
                    script {
                        withDockerRegistry(url: 'https://registry:5000', credentialsId: 'registry-login') {
                            dockerImage.push()
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            slackSend (channel: '#jenkins', color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
        failure {
            slackSend (channel: '#jenkins', color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
        }
    }
}