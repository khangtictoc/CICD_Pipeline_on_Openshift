def call(boolean isFailed, def tagName, def branch){
    if (isFailed == true){
        rollBack(branch, tagName)
    }
    else{
        rollOut(branch)
    }
}

def rollOut(def branch){
    sh "/kaniko/executor --context . --dockerfile ./docker/Dockerfile --destination 10.98.86.99:8082/khangth11/${branch}/mkyong:stable --force --insecure-pull --skip-tls-verify"
}

def rollBack(def branch, def tagName){
    sh "/kaniko/executor --context . --dockerfile ./docker/Dockerfile --destination 10.98.86.99:8082/khangth11/${branch}/mkyong:${tagName} --force --insecure-pull --skip-tls-verify"
}