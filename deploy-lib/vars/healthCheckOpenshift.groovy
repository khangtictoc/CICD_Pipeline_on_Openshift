import com.example.Constants
//import com.example.SlackNotify

def call(Map config){
    sleep(time: 30, unit: 'SECONDS')

    def CHECK_HEATH = sh(
        returnStdout: true,
        script: "curl -I http://service-mkyong-${config.branch}.khangth11.svc.cluster.local:${config.websvcPort} | head -n 1 | cut -d ' ' -f2"
    ).trim() 

    if (CHECK_HEATH =~ /^20[0-9]$/){
        return true
    }
    else{
        return false
    }
}