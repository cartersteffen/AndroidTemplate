pipeline {
  agent none
  environment {
   
      APP_NAME = 'AndroidTemplate'
      
      APPCENTER_BASEURL = 'https://api.appcenter.ms/'
      APPCENTER_APITOKEN = credentials('AppCenter_API_Token')
      APPCENTER_ORG = 'cartersteffen
  }
  stages {
    stage('Checkout') {
      when {
        anyOf {
          branch 'master' 
        }
      }
      agent {
          label 'docker-android'
      }
      steps {
        checkout scm
        script{
          shaName = sh returnStdout: true, script: \'git rev-parse HEAD\'
          shaName = shaName.trim()
          branchName = env.BRANCH_NAME
        }
        echo "shaName: $shaName
        echo "branchname: $branchName
        
        stash name:'Repo'
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
        when {
          anyOf {
            branch 'master' 
          }
        }
      agent {
          label 'docker-android'
      }
        steps {
          script {
            FAILED_STAGE=env.STAGE_NAME
            unstash 'APKs'
            if(env.BRANCH_NAME == "master") {
              uploadToAppCenter(APK_DEV, APPCENTER_DEV_APP, APPCENTER_DISTRIBUTION) 
            }
          }
        }
      }

    }
  }
def uploadToAppCenter(apk, appName, distro) {
  def secureUploadUrl = sh returnStdout: true, script: "curl -X POST --header 'Content-Type: application/json' --header 'X-API-Token: ${APPCENTER_APITOKEN}' 'https://api.appcenter.ms/v0.1/apps/${APPCENTER_ORG}/${appName}/release_uploads'"
  def uploadInfo = parseJsonText(secureUploadUrl)
  
  sh script: "curl -F 'ipa=@$apk' $uploadInfo.upload_url
  
  def releaseUrl = sh returnStdout: true, script: """curl -X PATCH --header "Content-Type: application/json' --header 'X-API-Token: ${APPCENTER_APITOKEN}' -d {"status": "committed"}' 'https://api.appcenter.ms/v0.1/apps/${APPCENTER_ORG}/${appName}/release_uploads/$uploadInfo.upload_id'"""
  def releaseInfo = parseJsonText(releaseUrl)
  
  sh script: """curl -X PATCH --header 'Content-Type: application/json  --header 'X-API-Token: ${APPCENTER_APITOKEN}' -d {"destination_name": "$distro", "release_notes": "" }' 'https://api.appcenter.ms/$releaseInfo.release_url'"""
}
