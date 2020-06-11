pipeline {
    environment {
    registry = "9962281904/docker-test"
    registryCredential = 'dockerhub'
    dockerImage = ''
  }
  agent any
  stages {
    stage('Building image') {
      steps{
        script {
          echo ":$BUILD_NUMBER"
          echo ":$GIT_COMMIT"
          echo "${GIT_COMMIT[0..7]}"
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }
  }
}
