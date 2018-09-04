pipeline { 
	
	agent {   
		label 'Slave_Induccion'
	} 
	
	 
	options {
	      
	       buildDiscarder(logRotator(numToKeepStr: '3'))
		
	       disableConcurrentBuilds() 
	}
	
	
	tools {    
		jdk 'JDK8_Centos'   
		gradle 'Gradle4.5_Centos' 
	}
	
	 
	stages{  
		
		stage('Checkout') {     
			steps{        
				echo "------------>Checkout<------------"     
				checkout([$class: 'GitSCM', branches: [
			        [name: '*/master']
		                ], doGenerateSubmoduleConfigurations: false, extensions: [], gitTool: 'Git_Centos', submoduleCfg: [], userRemoteConfigs: [
			        [credentialsId: 'DanielGiraldo-GitHub', url: 'https://github.com/DanielGiraldo-GitHub/Proyecto-estacionamiento']
	                 	]])
			}  
		}    
		
		stage('Unit Tests') {    
			steps{    
				echo "------------>Unit Tests<------------"     
			}   
		}  
		
		stage('Integration Tests') {  
			steps {   
				echo "------------>Integration Tests<------------"    

			}    
		}  
		
		stage('Static Code Analysis') {    
			steps{       
				echo '------------>Analisis de codigo estatico<------------'  
		
				 withSonarQubeEnv('Sonar') {
                 sh "${tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dsonar.projectKey = DanielGiraldo-GitHub_Proyecto-estacionamiento -Dsonar.organization = danielgiraldo-github-github -Dsonar.host.url = https: //sonarcloud.io -Dsonar.login = 5a06372bf6d3fb6d1892551dd1e6950b9a33dd8c"
           }
          }
}
stage('Build') {
	steps {
		echo "------------>Build<------------"
		sh 'gradle --b ./build.gradle build -x test'
	} 
}
}
post {
	always {
		echo 'This will always run'
	}
	success {
		echo 'This will run only if successful'
		junit '**/build/test-results/test/*.xml'
	}
	failure {
		echo 'This will run only if failed'
		mail (to: 'daniel.giraldo@ceiba.com.co',subject: "FailedPipeline:${currentBuild.fullDisplayName}",body: "Something is wrong with ${env.BUILD_URL}")
	}
	unstable {
		echo 'This will run only if the run was marked as unstable'
	}
	changed {
		echo 'This will run only if the state of the Pipeline has changed'
		echo 'For example, if the Pipeline was previously failing but is now successful'
	}
}
}
