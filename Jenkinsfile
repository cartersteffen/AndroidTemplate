pipeline {
  agent any
  environment {
   
      APP_NAME = 'AndroidTemplate'
      MODULE_NAME = 'app'
    
      //Environments
      DEV_ENV = "DEV"
      UAT_ENV = "UAT"
      STAGE_ENV = "STAGE"
      PROD_ENV = "PROD"

      //Output APK names per environment
      APK_DEV = "temp_dev.apk"
      APK_UAT = "temp_uat.apk"
      APK_STAGE = "temp_stage.apk"
      APK_PROD = "temp_prod.apk"
      
      APPCENTER_BASEURL = 'https://api.appcenter.ms/'
      APPCENTER_APITOKEN = credentials('AppCenter_API_Token')
      APPCENTER_ORG = 'cartersteffen'
    
      //Variables for script use
      branchName = ""
      repoName = "AndroidTemplate"
      shaName = ""
      FAILED_STAGE = ' '
    
  }
  stages {
    stage('Checkout') {
      when {
        anyOf {
          branch 'master' 
        }
      }
      steps {
        checkout scm
        script{
          shaName = sh returnStdout: true, script: 'git rev-parse HEAD'
          shaName = shaName.trim()
          branchName = env.BRANCH_NAME
        }
        echo "shaName: $shaName"
        echo "branchname: $branchName"
        
        stash name:'Repo'
      }
    }

      stage('Test/Build') {
        when {
              anyOf {
                branch 'master' 
              }
        }
        parallel {
          stage('Unit Test') {
            steps {
              echo 'Tests'
            }
          }

          stage('Build') {
            steps {
              script {
                unstash 'Repo'
                
                sh """
                    export PATH=$ANDROID_HOME/tools:$PATH
                    chmod +x ./gradlew
                    ./gradlew ktlint
                """
                
                buildUnsignedApk(DEV_ENV)
                signApk(DEV_ENV)
                if (env.BRANCH_NAME == 'stage') {
                  buildUnsignedApk(UAT_ENV)
                  signApk(UAT_ENV)
                  
                  buildUnsignedApk(STAGE_ENV)
                  signApk(STAGE_ENV)
                } else if(env.BRANCH_NAME == 'prod') {
                  buildUnsignedApk(PROD_ENV)
                  signApk(PROD_ENV)
                }
                
                stash includes: "*.apk", name: 'APKs', useDefaultExcludes: false
              }
              post {
                always {
                  deleteDir() 
                }
              }
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
  
  sh script: "curl -F 'ipa=@$apk' $uploadInfo.upload_url"
  
  def releaseUrl = sh returnStdout: true, script: """curl -X PATCH --header "Content-Type: application/json' --header 'X-API-Token: ${APPCENTER_APITOKEN}' -d {"status": "committed"}' 'https://api.appcenter.ms/v0.1/apps/${APPCENTER_ORG}/${appName}/release_uploads/$uploadInfo.upload_id'"""
  def releaseInfo = parseJsonText(releaseUrl)
  
  sh script: """curl -X PATCH --header 'Content-Type: application/json  --header 'X-API-Token: ${APPCENTER_APITOKEN}' -d {"destination_name": "$distro", "release_notes": "" }' 'https://api.appcenter.ms/$releaseInfo.release_url'"""
}

def buildUnsignedApk(inEnv){
  switch(inEnv){
    case DEV_ENV:
      sh '''
          export ANDROID_HOME=/tools/androidsdk
          export PATH=$ANDROID_HOME/tools:$PATH
          . /etc/profile.d/jenkins.sh
          ./gradlesw assembleDevRelease
      '''
      break;
    case UAT_ENV:
      sh '''
          export ANDROID_HOME=/tools/androidsdk
          export PATH=$ANDROID_HOME/tools:$PATH
          . /etc/profile.d/jenkins.sh
          ./gradlesw assembleUatRelease
      '''
    break;
    case STAGE_ENV:
      sh '''
          export ANDROID_HOME=/tools/androidsdk
          export PATH=$ANDROID_HOME/tools:$PATH
          . /etc/profile.d/jenkins.sh
          ./gradlesw assembleStageRelease
      '''
    break;
    case PROD_ENV:
      sh '''
          export ANDROID_HOME=/tools/androidsdk
          export PATH=$ANDROID_HOME/tools:$PATH
          . /etc/profile.d/jenkins.sh
          ./gradlesw assembleProdRelease
      '''
    break;
  }
}

def signApk(inEnv){
  def FLAVOR="dev"
  def outputApk = "$APK_DEV"
  
  switch(inEnv) {
    case DEV_ENV:
        FLAVOR = "dev"
        outputApk = "$APK_DEV"
        break;
    case UAT_ENV:
        FLAVOR = "uat"
        outputApk = "$APK_UAT"
        break;
    case STAGE_ENV:
        FLAVOR = "stage"
        outputApk = "$APK_STAGE"
        break;
    case PROD_ENV:
        FLAVOR = "prod"
        outputApk = "$APK_PROD"
        break;
  }
  def INPUT_APK = "${MODULE_NAME}/build/outputs/apk/${FLAVOR}/release/${MODULE_NAME}-${FLAVOR}-release-unsigned.apk"
  def SIGNED_APK = "${MODULE_NAME}/build/outputs/apk/${FLAVOR}/release/${MODULE_NAME}-${FLAVOR}-release-signed.apk"
  def ALIGNED_APK = "${MODULE_NAME}/build/outputs/apk/${FLAVOR}/release/${MODULE_NAME}-${FLAVOR}-release-aligned.apk"
  withCredential([file(credentialsId: 'android-preprod-keystore', variable: 'keystore'), string(credentialsId: 'android-preprod-keystore-password', variable: 'password')])
  {
      sh"""
          /tools/androidsdk/build-tools/$ANDROID_BUILD_TOOLS_VERSION/zipalign -f -v -p 4 $INPUT_APK $ALIGNED_APK
          /tools/androidsdk/build-tools/$ANDROID_BUILD_TOOLS_VERSION/apksigner sign --ks $keystore --ks-pass pass:$password --out $outputApk ${ALIGNED_APK}
      """
  }
}
