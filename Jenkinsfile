pipeline {
    agent any
    stages {
        stage('Build Auth') {
            steps {
                build job: 'organice-auth', wait: true
            }
        }
        stage('Build') { 
            steps {
                sh 'mvn clean package'
            }
        }      
        stage('Build Image') {
            steps {
                script {
                    auth = docker.build("alfredjynx/auth:${env.BUILD_ID}", "-f Dockerfile .")
                }
            }
        }
        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                        auth.push("${env.BUILD_ID}")
                        auth.push("latest")
                    }
                }
            }
        }
    }
}