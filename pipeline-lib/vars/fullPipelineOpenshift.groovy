import com.example.Constants


def getdeployPort(branch){
    switch(branch){
        case ~/uat(\/\w+)*/:
            return Constants.UAT_PORT
        case ~/dev(\/\w+)*/:
            return Constants.DEV_PORT
        case 'main':
            return Constants.MAIN_PORT
        default:
            return Constants.MAIN_PORT
    }
}

def call(){
    def cons = new Constants(
        env.BUILD_NUMBER,
        env.BUILD_URL,
        env.BUILD_TIMESTAMP,
        env.BRANCH_NAME
    )

    def port = getdeployPort(env.BRANCH_NAME)

    def tagName = cons.getBuildNum() + '-' +
                cons.getTimestamp() + '-' +
                cons.getBranch() + '-' +
                cons.getGitHash()

    properties([gitLabConnection('khangth11-gitlab')])

    podTemplate(
        cloud: 'kubernetes-khangth11',
        yaml: readTrusted('k8s/podTemplate/template.yml'),
        serviceAccount: 'sa-khangth11'
    ) { 
        node(POD_LABEL) {
            stage('CLONE') {
                def scmVars = gitCheckoutOpenshift([
                    branch: env.BRANCH_NAME =~ /^MR/ ? env.CHANGE_BRANCH : cons.getBranch(),
                    repoURL: Constants.GITLAB_REPO_URL
                ])
                cons.setGitHash(scmVars.GIT_COMMIT)
            }
            stage('GIT NOTIFY') {
                gitNotifyOpenshift()
            }
            stage('BUILD JAR') {
                container('maven') {
                    srcCompileOpenshift()
                }
            }
            // stage('UNIT TEST') {
            //     container('maven'){
            //         srcTestOpenshift()
            //     }
            // }
            // stage('SONAR SCAN') {
            //     container('maven'){
            //         sonarScanOpenshift()
            //     }
            // }
            stage('BUILD IMAGE') {
                container('kaniko'){
                    buildImgOpenshift(cons.branch)
                }
            }
            stage('DEPLOY WEB') {
                container('helm'){
                    deployWithHelm(
                        './helm/mkyong',
                        "values-${cons.branch}.yaml",
                        cons.branch
                    )
                }
            }
            stage('HEALTH CHECK') {
                def isFailed = healthCheckOpenshift(
                    [
                        branch: cons.branch,
                        websvcPort: Constants.WEB_SVC_PORT
                    ]
                )
                cons.setIsFailed(isFailed)
            }

            stage('ROLLING BACK') {
                container('kaniko'){
                    rollingBackOpenshift(
                        cons.getIsFailed(),
                        tagName,
                        cons.getBranch()
                    )
                }
            }
        }
    }
}