def call(){
    sh 'mvn --settings /home/jenkins/settings.yml -Dcheckstyle.skip test'
}