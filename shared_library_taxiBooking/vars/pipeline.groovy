def call() {
    pipeline {
        agent any

        stages {
            stage('Cleanup') {
                steps {
                    sh 'rm -rf taxi-booking'  // Delete old project
                }
            }

            stage('Print') {
                steps {
                    echo 'new project'
                }
            }

            stage('Clone') {
                steps {
                    sh 'git clone -b dev https://github.com/reddi122/taxi-booking.git'
                }
            }

            stage('Build') {
                steps {
                    dir("taxi-booking") {
                        sh 'mvn clean package'
                    }
                }
            }
	    stage('deployment') {
                steps {
                    dir ("taxi-booking") {
                        sh 'cp target/*.war /opt/tomcat/webapps'
                    }
                }
            }	

            stage('Archive Artifacts') {
                steps {
                    archiveArtifacts artifacts: 'taxi-booking/taxi-booking/target/*.war', fingerprint: true
                }
            }
        }
    }
}

