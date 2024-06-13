#! /usr/bin/env groovy

pipeline {

  agent {
    label 'maven'
  }

  stages {
    stage('Build') {
      steps {
        echo 'Building..'
        sh 'mvn clean package'
        // Add steps here
      }
    }
    stage('Create Container Image') {
      steps {
        echo 'Create Container Image..'
        
        script {
        openshift.withCluster() { 
          openshift.withProject("neetigya-amadeus-dev") {
          
            def buildConfigExists = openshift.selector("bc", "demo").exists() 
            
            if(!buildConfigExists){ 
              openshift.newBuild("--name=demo", "--docker-image=registry.redhat.io/jboss-eap-7/eap74-openjdk8-openshift-rhel7", "--binary") 
            } 
            
            openshift.selector("bc", "demo").startBuild("--from-file=target/spring-boot-app-0.0.1-SNAPSHOT.jar", "--follow") } }
          // Add steps here

        }
      }
    }
    stage('Deploy') {
      steps {
        echo 'Deploying....'
        script {

          // Add steps here
          openshift.withCluster() { 
  openshift.withProject("neetigya-amadeus-dev") { 
    def deployment = openshift.selector("dc", "demo") 
    
    if(!deployment.exists()){ 
      openshift.newApp('demo', "--as-deployment-config").narrow('svc').expose() 
    } 
    
    timeout(5) { 
      openshift.selector("dc", "demo").related('pods').untilEach(1) { 
        return (it.object().status.phase == "Running") 
      } 
    } 
  } 
}
        }
      }
    }
  }
}