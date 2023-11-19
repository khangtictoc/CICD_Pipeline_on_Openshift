def call(Map config){
    sh "mvn --settings /home/jenkins/settings.yml sonar:sonar"
}