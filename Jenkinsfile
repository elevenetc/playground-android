#!groovy
//import groovy.json.JsonSlurper

class Config {
    static String hockeyToken = env.HOKEY_TOKEN
    static String hockeyAppId = env.HOCKEY_APP_ID
    static String apkFileName = env.APK_FILE_NAME
}

node {
    checkout()
    //compile()
    loadApps()
    //createApp()
    //createVersion()
    //upload()

    //env.BUILD_NUMBER

}

def checkout() {
    checkout scm
}

def compile() {
    sh './gradlew assembleDebug'
}

def upload() {

    //TODO: add commits to notes
    //TODO: inform testers
    //TODO: separate name
    def branchName = env.BRANCH_NAME
    sh 'echo 9999999999999999'
    sh 'echo $BRANCH_NAME'
    sh 'echo 9999999999999999'
    sh 'mv app/build/outputs/apk/app-debug.apk app/build/outputs/apk/app-debug-x.apk'
    sh 'curl -F "status=2" -F "notify=1" -F "notes=Some new features and fixed bugs." -F "notes_type=0" -F "ipa=@app/build/outputs/apk/app-debug-x.apk" -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/upload'
}

def createApp() {
    sh 'curl \
		-F "title=wallet-$BRANCH_NAME" \
		-F "bundle_identifier=wallet-$BRANCH_NAME" \
		-F "platform=Android" \
		-F "release_type=1" \
		-H "X-HockeyAppToken: "' + Config.hockeyToken + 'https://rink.hockeyapp.net/api/2/apps/new'
}

def createVersion() {
    sh 'curl -F "bundle_version=$BRANCH_NAME" -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/' + Config.hockeyAppId + '/app_versions/new'
}

def loadApps() {

    //def js = new groovy.json.JsonSlurperClassic().parseText("{}")


    def jsonResp = sh 'curl -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps'

    def response = parseJson(jsonResp)
    def status = response.status
    def apps = response.apps

    if (status == "success")
        sh 'echo 666'
    else
        sh 'echo 999'

    //def zzz = 1
    //def object = JSON.parse(resp)
    //def status = response.status
    //sh "echo 111"
    //sh "echo ${status}"
    //sh "echo 111"
    //def result = new URL("https://rink.hockeyapp.net/api/2/apps").getText()
    //sh 'echo ${result}'

//def response = httpRequest "http://httpbin.org/response-headers?param1=${param1}"


}

//@NonCPS
//def parseJson(text) {
//  return new JsonSlurper().parseText(text)
//}

def parseJson(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}