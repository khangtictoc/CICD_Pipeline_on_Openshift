def call(def branch){
    sh "/kaniko/executor --context . --dockerfile ./docker/Dockerfile --destination 10.98.86.99:8082/khangth11/${branch}/mkyong:latest --force --insecure-pull --skip-tls-verify"
}