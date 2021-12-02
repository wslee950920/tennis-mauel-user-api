pipeline {
    agent {
        kubernetes {
            yamlFile 'menifests/agents.yaml'
        }
    }
    options { skipDefaultCheckout(true) }
    environment {
        REGISTRY_CERTS = credentials('docker-registry')
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

        stage('Unit Test') {
            steps {
                container('gradle') {
                    sh 'gradle test'
                }
            }
        }

        stage('Integration Test') {        
            steps {
                container('gradle') {
                    //TODO: 추후 통합테스트로 변경
                    sh 'gradle test'
                }
            }
        }

        stage('Build a Gradle project') {
            steps {
                container('gradle') {
                    sh 'gradle build -x test'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                container('gradle') {
                    withSonarQubeEnv('sonarqube') {
                        sh "gradle sonarqube"
                    }
                }

                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Code Coverage') {
            steps {
                container('gradle') {
                    sh 'gradle jacocoTestCoverageVerification'
                }
            }
        }

        stage('Build a Docker image') {
            steps {
                container('docker') {
                    sh 'docker build -t registry:5000/tennis-mauel-user-api:$BUILD_ID .'
                }
            }
        }

        stage('Push a Docker image') {
            steps {
                container('docker') {
                    sh 'echo $REGISTRY_CERTS_PSW | docker login registry:5000 -u $REGISTRY_CERTS_USR --password-stdin'
                    sh 'docker push registry:5000/tennis-mauel-user-api:$BUILD_ID'

                    sh 'docker rmi registry:5000/tennis-mauel-user-api:$BUILD_ID'
                }
            }
        }

        stage('Deploy an App') {
            steps {
                container('kubectl') {
                    sh 'echo $KUBECONFIG'
                    sh 'ls -l /root/.kube'
                    sh 'ls -l /root/.kube/config'
                    sh 'cat /root/.kube/config'
                    sh 'kubectl get node'
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

        always {
            junit '**/build/test-results/test/*.xml'
            jacoco( 
                execPattern: '**/build/jacoco/*.exec',
                classPattern: '**/build/classes/java/main',
                sourcePattern: 'src/main/java',
                runAlways: true
            )
        }
    }
}