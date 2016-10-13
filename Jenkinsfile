#!groovy
import groovy.json.JsonSlurperClassic

class Config {
    static String hockeyToken
    static String hockeyAppId
    static String apkFileName
    static String appName = 'Sample'

    static String tempFileName = 'temp.txt'
}

node {

    Config.hockeyToken = env.HOKEY_TOKEN
    Config.hockeyAppId = env.HOCKEY_APP_ID
    Config.apkFileName = env.APK_FILE_NAME

    checkout()
    compile()
    loadApps()
    createApp()
//    createVersion()
//    upload()

}

def checkout() {
    println 'METHOD: checkout'
    checkout scm
}

def compile() {
    println 'METHOD: compile'
    sh './gradlew assembleDebug'
}

def upload() {
    println 'METHOD: upload'
    //TODO: add commits to notes
    //TODO: inform testers
    //TODO: separate name
    def branchName = env.BRANCH_NAME
    sh 'echo 9999999999999999'
    sh 'echo $BRANCH_NAME'
    sh 'echo 9999999999999999'
    sh 'mv app/build/outputs/apk/app-debug.apk app/build/outputs/apk/app-debug-x.apk'
    sh 'curl -F "status=2" -F "notify=1" -F "notes=Some new features and fixed bugs." -F "notes_type=0" -F "ipa=@app/build/outputs/apk/app-debug-x.apk" -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/upload > ' + Config.tempFileName
    checkError()
}

def createApp() {
    println 'METHOD: createApp'
    request('-F "title=' + Config.appName + '-$BRANCH_NAME" -F "bundle_identifier=' + Config.appName + '-$BRANCH_NAME" -F "platform=Android" -F "release_type=1" -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/new')
}

def createVersion() {
    println 'METHOD: createVersion'
    sh 'curl -F "bundle_version=$BRANCH_NAME" -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/' + Config.hockeyAppId + '/app_versions/new > ' + Config.tempFileName
    checkError()
}

def loadApps() {
    println 'METHOD: loadApps'

    def response = request('-H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps')

//    sh 'curl -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps > ' + Config.tempFileName
//    def response = parseJson(readFile(Config.tempFileName))
//    checkError(response)

    def status = response.status
    def apps = response.apps

    if (status == 'success') {

    } else {

    }

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

static def parseJson(String json) {
    return new JsonSlurperClassic().parseText(json)
}

def checkError(response) {
    if (response == null || response.status != 'success')
        throw new Exception('Error: ' + response);
}

def request(curlParams) {
    sh 'curl ' + curlParams + ' > ' + Config.tempFileName
    def response = parseJson(readFile(Config.tempFileName))
    checkError(response)
    println response
    return response
}