def call(){
    sh "mvn --settings /home/jenkins/settings.yml -DskipTests -Dcheckstyle.skip package" 
}

