def call(def chartDir, def valueFile, def branch){
    sh "helm upgrade --install -f  $chartDir/values/$valueFile mkyong-app-${branch} $chartDir"
}