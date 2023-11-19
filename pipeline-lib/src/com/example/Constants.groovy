package com.example

class Constants{
    def buildNum
    def buildUrl
    def timestamp
    def branch

    def gitHash
    def version
    def relFileJar
    def relJarURL

    def isFailed

    Constants(def buildNum, def buildUrl, def timestamp, def branch){
        this.buildNum = buildNum
        this.buildUrl = buildUrl
        this.timestamp = timestamp
        this.branch = branch
    }

    def getBuildNum(){
        return buildNum
    }

    def getBuildUrl(){
        return buildUrl
    }

    def getTimestamp(){
        return timestamp
    }

    def getBranch(){
        return branch
    }

    def getGitHash(){
        return gitHash
    }

    def setGitHash(def gitHash){
        this.gitHash = gitHash
        this.version = buildNum + '-' +
                       timestamp + '-' +
                       branch + '-' +
                       gitHash
        this.relFileJar = "${this.TARGET_RELEASE}-${this.version}.jar"
        this.relJarURL = "${this.PROTOCOL}://${this.NEXUS_USER}:${this.NEXUS_PASS}@${this.NEXUSIP}:${this.NEXUSPORT}" +
                         "/repository/vprofile-release/${this.TARGET_GROUP}" +
                        "/${this.TARGET_RELEASE}/${this.version}/${this.relFileJar}"
    }

    def setIsFailed(def isFailed){
        this.isFailed = isFailed
    }

    def getIsFailed(){
        return this.isFailed
    }

    def getRelFileJar(){
        return relFileJar
    }

    def getRelJarURL(){
        return relJarURL
    }

    def getVersion(){
        return version
    }

    def slackSuccessMsg(){
        return """\
                |*SUCCESS* on branch ${this.branch}\n
                |Everything works correctly. Enjoy !!!\n
                |(${this.timestamp}) Job jenkins-maven build number ${this.buildNum}\n
                |More info at: ${this.buildUrl}""".stripMargin()
    }

    def slackFailMsg(){
        return """\
                |*FAILED* on branch ${this.branch}\n
                |(${this.timestamp}) Job jenkins-maven build number ${this.buildNum}\n
                |Something wrong happened. Carefully check it again !!!\n
                |More info at: ${this.buildUrl}""".stripMargin()
    }

    static final APP_NAME = 'petclinic'
    static final DOCKER_BUILD = false

    /////// GITLAB //////

    static final GITLAB_USER = 'khangth11'
    static final GITLAB_PASS = 'Darkhero101@'
    static final GITLAB_IP = '10.98.86.101'
    static final GITLAB_REPO_URL="http://${GITLAB_USER}:${GITLAB_PASS}@${GITLAB_IP}/jenkinspipeline/khangth11-shared-library/spring-data-jpa-mysql.git"
    
    ////// NEXUS //////

    // General
    static final NEXUS_USER = 'admin'
    static final NEXUS_PASS = 'root'
    static final NEXUSIP = '192.168.56.20'
    static final NEXUSPORT = '8081'
    static final NEXUS_LOGIN = 'nexuslogin'

    // Repo for Artifact
    static final SNAP_REPO_ARTIFACT = 'vprofile-snapshot'
    static final RELEASE_REPO_ARTIFACT = 'vprofile-release'
    static final CENTRAL_REPO_ARTIFACT = 'vpro-maven-central'
    static final GRP_REPO_ARTIFACT = 'vpro-maven-group'

    // Repo for Docker image
    static final SNAP_REPO_DOCKER = 'docker-snapshot'
    static final RELEASE_REPO_DOCKER = 'docker-release'
    static final RELEASE_DOCKER_HTTP_PORT = '8082'
    static final CENTRAL_REPO_DOCKER = 'docker-maven-central'
    static final GRP_REPO_DOCKER = 'docker-maven-group'

    // Uploading Infor
    static final NEXUS_VER = 'nexus3'
    static final PROTOCOL = 'http'
    static final RELEASE_ARTIFACT_NAME = 'spring-petclinic-3.1.0-SNAPSHOT'
    static final TARGET_RELEASE = "${APP_NAME}"
    static final TARGET_GROUP = 'developer'
    static final FILE_TYPE = 'jar'

    ////// DOCKER  //////

    static final IMAGE_NAME = "${APP_NAME}-app"
    static final IMAGE_LATEST = "${IMAGE_NAME}:latest"
    static final IMAGE_STABLE = "${IMAGE_NAME}:stable"

    static final DOCKER_COMPOSE_PATH = 'docker/docker-compose.yml'
    static final DOCKERFILE_PATH = 'docker/Dockerfile'
    static final PULL_URL = "${NEXUSIP}:${RELEASE_DOCKER_HTTP_PORT}/${IMAGE_LATEST}"
    static final DOCKER_URL = "${NEXUSIP}:${RELEASE_DOCKER_HTTP_PORT}"
    static final CONTAIN_NAME = "${APP_NAME}"
    static final UAT_PORT = 8090
    static final MAIN_PORT = 8080
    static final DEV_PORT = 8091

    ////// DEPLOYMENT HOST //////
    static final WEB_DOMAIN = 'mkyong.khangtictoc.com'
    static final WEB_PORT = "${MAIN_PORT}"
    static final REV_PROXY_PORT = '80'
    static final WEB_SVC_PORT = '8080'




    ////// SLACK NOTIFICATION //////
    static final TEAM_DOMAIN = 'darkhero101neverdie'
    static final SUCESS_CHANNEL = '#jenkins-cicd-success'
    static final FAIL_CHANNEL = '#jenkins-cicd-failed'
    static final BOT_ICON = 'resources/img/avatars-000706520767-yiqbpu-t500x500.jpg'
    static final BOT_NAME = 'loli-dam-dang'

    static final MESS_COLOR = '#439FE0'
    static final SLACK_TOKEN_ID = 'slack-notify-token'

}