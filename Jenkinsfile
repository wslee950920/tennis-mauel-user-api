node('master'){
    stage('Poll'){
        checkout scm
    }

    stage('Unit test'){
        sh './gradlew clean test'
        junit '**/build/test-results/test/*.xml'
    }

    stage('Build package'){
        sh './gradlew clean build -x test'
    }

    stage("Build Docker image"){
        app = docker.build("tennis-mauel-user-api:${env.BUILD_ID}", ".")
    }
}