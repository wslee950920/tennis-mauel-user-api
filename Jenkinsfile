pipeline {
    agent {
        kubernetes {
            yamlFile 'AgentPod.yaml'
            defaultContainer 'jnlp'
        }
    }
    parameters {
        string(name: 'REGISTRY', defaultValue: 'registry-docker-registry.registry.svc.cluster.local:5000')
    }
    
    stages {
        stage('git') {
            steps {
                git 'https://github.com/wslee950920/tennis-mauel-user-api.git'
            }
        }

        //한개의 stage에는 한개의 steps만
        stage('Unit Test a Gradle project') {        
            steps {
                container('gradle') {
                    sh 'gradle test'
                }
            }
        }

        //통합 테스트를 병렬로 수행해보려 하였으나 알 수 없는 이유로 계속 실패
        //TODO: 추후 통합테스트로 변경
        stage('Integration Test a Gradle project') {        
            steps {
                container('gradle') {
                    sh 'gradle test'
                }

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
            steps {
                container('docker') {
                    sh "docker build -t tennis-mauel-user-api:${env.BUILD_ID} ."
                }
            }
        }

        stage('Push a Docker image') {
            steps {
                container('docker') {
                    sh "docker tag tennis-mauel-user-api:${env.BUILD_ID} ${params.REGISTRY}/tennis-mauel-user-api"
                    sh "docker push ${params.REGISTRY}/tennis-mauel-user-api:${env.BUILD_ID}"
                }
            }
        }
    }
}