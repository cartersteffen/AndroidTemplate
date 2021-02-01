pipeline {
  agent none
  stages {
    stage('Checkout') {
      agent {
        docker {
          image 'docker-android'
        }

      }
      steps {
        sh '''script{
    shaName = sh returnStdout: true, script: \'git rev-parse HEAD\'
    shaName = shaName.trim()
    branchName = env.BRANCH_NAME
}'''
        }
      }

      stage('Test/Build') {
        parallel {
          stage('Unit Test') {
            steps {
              git(url: 'https://github.com/cartersteffen/AndroidTemplate.git', branch: 'master')
            }
          }

          stage('Build') {
            steps {
              echo 'Build APK'
            }
          }

        }
      }

      stage('Upload to AppCenter') {
        steps {
          echo 'Upload to AppCenter'
        }
      }

    }
  }