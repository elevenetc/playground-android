#!groovy
import groovy.json.JsonSlurperClassic

class Config {
    static String hockeyToken
    static String hockeyAppId
    static String apkFileName
    static String appName = 'Sample'

    static String tempFileName = 'temp.txt'

    static boolean debug = true
}

node {

    Config.hockeyToken = env.HOKEY_TOKEN
    Config.hockeyAppId = env.HOCKEY_APP_ID
    Config.apkFileName = env.APK_FILE_NAME

    log 'Build number: ' + env.BUILD_NUMBER

    checkout()
    compile()
    def app = loadOrCreateApp()
    uploadVersionIfNeeded(app)


}

def loadVersions(app) {
    def id = app.public_identifier
    return request('-H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/' + id + '/app_versions').app_versions
}

def uploadVersionIfNeeded(app) {
    logMethod('uploadVersionIfNeeded')
    def versions = loadVersions(app)
    String lastCommitMessage = getLastCommitMessage()
    boolean needToUpload = true
    for (int i = 0; i < versions.size(); i++) {
        String notes = versions.get(i).notes
        if (lastCommitMessage == notes) {
            needToUpload = false
            break
        }
    }
    if (needToUpload) uploadVersion(app)
}

def loadOrCreateApp() {
    List apps = loadApps()
    def app = null
    def title = getAppTitle()

    checkAndDeleteApps(apps)

    for (int i = 0; i < apps.size(); i++) {
        def a = apps.get(i)
        if (a.title == title) {
            app = a
            log('App already created for ' + title)
            break
        }
    }

    if (app == null) {
        app = createApp()
        log('New app was created for ' + title)
    }

    return app
}

def checkAndDeleteApps(apps) {

    String branches = execGit('branch -a')
    boolean wasSomethingDeleted = false

    for (int i = 0; i < apps.size(); i++) {
        def app = apps.get(i)
        String initBranch = app.title.replace(Config.appName + '-', '')

        if (!branches.contains(initBranch)) {
            log('DELETE APP:' + app.title)
            wasSomethingDeleted = true
        }
    }

    if (!wasSomethingDeleted) {
        log('NOTHING WAS DELETED')
    }
}

def getLastCommitMessage() {
    sh 'git log -1 --pretty=%B > ' + Config.tempFileName
    return readFile(Config.tempFileName).trim()
}

def checkout() {
    println 'METHOD: checkout'
    checkout scm
}

def compile() {
    println 'METHOD: compile'
    sh './gradlew assembleDebug'
}

def uploadVersion(app) {
    log 'METHOD: uploadVersion'
    compile()
    sh 'mv app/build/outputs/apk/app-debug.apk app/build/outputs/apk/app-debug-x.apk'
    request('-F "status=2" -F "notify=1" -F "notes=' + getNotesForVersion() + '" -F "notes_type=0" -F "ipa=@app/build/outputs/apk/app-debug-x.apk" -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/' + app.public_identifier + '/app_versions/upload')
}

def uploadApp() {
    log 'METHOD: upload'
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
    log 'METHOD: createApp'
    def app = request('-F "title=' + Config.appName + '-$BRANCH_NAME" -F "bundle_identifier=' + Config.appName + '-$BRANCH_NAME" -F "platform=Android" -F "release_type=1" -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/new')
    return app
}

def getAppTitle() {
    return Config.appName + '-' + env.BRANCH_NAME
}

def getNotesForVersion() {
    def commitMessage = getLastCommitMessage()
    def hash = getGitHash()
    return commitMessage + '-' + hash
}

def getBundleId() {
    return Config.appName + '-' + env.BRANCH_NAME
}

def getGitHash() {
    sh 'git log --pretty=format:"%h" -n 1 > ' + Config.tempFileName
    return readFile(Config.tempFileName).trim()
}

def getCurrentBranches() {
    return execGit('branch -a')
}

String execGit(String command) {
    sh 'git ' + command + ' > ' + Config.tempFileName
    return readFile(Config.tempFileName).trim()
}

def createVersion() {
    log 'METHOD: createVersion'
    sh 'curl -F "bundle_version=$BRANCH_NAME" -H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps/' + Config.hockeyAppId + '/app_versions/new > ' + Config.tempFileName
    checkError()
}

List loadApps() {
    println 'METHOD: loadApps'
    def response = request('-H "X-HockeyAppToken: ' + Config.hockeyToken + '" https://rink.hockeyapp.net/api/2/apps')
    return response.apps
}

//@NonCPS
//def parseJson(text) {
//  return new JsonSlurper().parseText(text)
//}

static def parseJson(String json) {
    return new JsonSlurperClassic().parseText(json)
}

def checkError(response) {
    //if (response == null || response.status != 'success')
    //    throw new Exception('Error: ' + response);
}

def request(curlParams) {
    sh 'curl ' + curlParams + ' > ' + Config.tempFileName
    def response = parseJson(readFile(Config.tempFileName))
    checkError(response)
    println response
    return response
}

def log(String message) {
    if (Config.debug)
        println 'LOG: ' + message
}

def logMethod(String methodName) {
    log('METHOD: ' + methodName)
}