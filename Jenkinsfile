//GLOBAL Variables Definition
def pvc_name="mvn-home"
def serviceaccount = "jenkins-admin"
def pvclabel = "pvc-creation-pod"
def cartridge_name = "java"
def cleanuplabel = "postbuild-cleanup-pod"
//def librarylabel = "built-in"
//Added for starting podname with the name of te pod and build number
def PODNAME="${JOB_NAME.replace("_","").split('/')[1]}"
def label = PODNAME+"-${BUILD_ID}"
//def GIT_URL=env.GIT_URL 
def GIT_CREDENTIAL_ID ='gitlab'
//If New Container-registry reference is required , we need to change both the below variable 'GCR_HUB_ACCOUNT_IMAGEBUILD' & 'GCR_HUB_ACCOUNT' with the same reference.
def GCR_HUB_ACCOUNT_IMAGEBUILD = 'gitlab:8223'
def GCR_HUB_ACCOUNT = 'localhost:32121'
def GCR_HUB_ACCOUNT_NAME = 'root'
def GCR_HUB_REPO_NAME="docker_registry"
def DOCKER_IMAGE_NAME = 'microservice'
def IMAGE_TAG = "java_${BUILD_ID}"
def IMAGE_TAG_INPUT = "java_${BUILD_ID}"
env.JOBNAME = "${JOB_NAME.split('/')[1]}"
//def K8S_DEPLOYMENT_NAME = 'java'
def NAMESPACE="ethan"
def qualityGateName='java_QG'
def sonarProjectname='TodoJava'
nameSpace="microsvc"
deploy_env="dev"
def sonarHostUrl="http://sonar.${NAMESPACE}.svc.cluster.local:9001/sonar"
def buildTool = "maven"
//def kubectl_image = GCR_HUB_ACCOUNT+"/"+GCR_HUB_ACCOUNT_NAME+"/"+GCR_HUB_REPO_NAME+"/aiindevops.azurecr.io/docker-kubectl:19.03-alpine"
def kubectl_image = GCR_HUB_ACCOUNT+"/"+GCR_HUB_ACCOUNT_NAME+"/"+GCR_HUB_REPO_NAME+"/docker-kubectl:19.03-alpine"
def GIT_BRANCH=scm.branches[0].name.split("/")[1]
//Set to true to enable influx. Set to false to disable
env.INFLUXDB=true
env.TRIVY_DB_UPDATE=false
println(GIT_BRANCH)

//Import Shared Library from SCM
library identifier: "jenkins_shared_library@${GIT_BRANCH}", retriever: modernSCM(
  [$class: 'GitSCMSource',
   remote: 'http://gitlab:8084/gitlab/root/jenkins_shared_library.git',
   credentialsId: 'gitlab'])

podTemplate(label: pvclabel, serviceAccount: serviceaccount, containers: [
  containerTemplate(name: 'kubectl', image: kubectl_image, ttyEnabled: true, command: 'cat')], imagePullSecrets: ['gcrcred']) {

  node(pvclabel) {
    stage('Pre-requisite validation') 
    {         
      container('kubectl') 
      { 
        //namespaceValidation(nameSpace)
        pvccreation(pvc_name,cartridge_name)
        gitCheckout(GIT_CREDENTIAL_ID)
        list = ['trivy','maven', 'curl', 'jmeter', 'kubectl', 'nodejs', 'kaniko']
       	generatePodTemplate(list,pvc_name,buildTool,GCR_HUB_ACCOUNT,GCR_HUB_ACCOUNT_NAME,GCR_HUB_REPO_NAME,cartridge_name) 
        influxJenkinsTargetCreation()
      }    
    }
  }
}

podTemplate(label: label, serviceAccount: serviceaccount, yaml: finalTemplate) {

    node(label) 
    { 
      timestamps 
      {

        try 
        {
 
            stage('Git Checkout') 
            { 
                
                gitCheckout(GIT_CREDENTIAL_ID)
            }
            stage('Extract Stage Details') 
            { 
                script{
                    def containerStages = readYaml text: finalTemplate
                    env.containerList = containerStages.spec.containers.name
                }
            println(env.containerList)
            }
            stage('build')
			{
				container('maven')
				{
					sh '''
					java -version
					mvn clean install -DskipTests package
					'''
				}
		}

        stage('unit tests')
			{
				container('maven')
				{
					sh '''
					echo 'test completed'
					'''
				}
		}
        stage('SonarQube Analysis') {
			withCredentials([usernamePassword(credentialsId: 'sonar-creds', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]){
			//withSonarQubeEnv('SonarQube') {
			
		
				withSonarQubeEnv('SonarQube') {
                println('Sonar Method enter');
				def scannerHome = tool 'SonarQube';
				sh "${scannerHome}/bin/sonar-scanner -Dsonar.login=$USERNAME -Dsonar.password=$PASSWORD";
                echo "Access the SonarQube URL from the Platform Dashboard tile"
				              
				}
			}
		} 
		if(env.containerList.contains('kaniko') == true)
        {        
          stage('Create Image')
          { 	
            container(name: 'kaniko') 
            {
              imageBuild(GCR_HUB_ACCOUNT_IMAGEBUILD,GCR_HUB_ACCOUNT_NAME,GCR_HUB_REPO_NAME,DOCKER_IMAGE_NAME,IMAGE_TAG)
            }  
          }
        }
    if(env.containerList.contains('kaniko') == true)
        {      
          stage('Publish Images')
          { 
            container(name: 'kaniko') 
            {
              imagePush(GCR_HUB_ACCOUNT_IMAGEBUILD,GCR_HUB_ACCOUNT_NAME,GCR_HUB_REPO_NAME,DOCKER_IMAGE_NAME,IMAGE_TAG)
            }   
          }
        }

    stage('deploy to dev')
		{
			container('kubectl'){
        wrap([$class: 'BuildUser']){
              def deploy_namespace = BUILD_USER.split(" ")[0]
              println(deploy_namespace)
          sh """
            sed -i 's/builduser/${deploy_namespace}/g' namespace.yaml
            sed -i 's/builduser/${deploy_namespace}/g' tomcat.yaml
            sed -i 's/builduser/${deploy_namespace}/g' tomcat-svc.yaml
            sed -i 's/builduser/${deploy_namespace}/g' mysql.yaml
            sed -i 's/builduser/${deploy_namespace}/g' mysql-pvc.yaml
            sed -i 's/tomcat-deployenv/microsvc${deploy_env}/g' tomcat.yaml
				    sed -i 's/deployenv/${IMAGE_TAG}/g' tomcat.yaml
				    sed -i 's/tomcat-deployenv/microsvc${deploy_env}/g' tomcat-svc.yaml
            sed -i 's/buildenv/mysql${deploy_env}db/g' mysql-pvc.yaml
            sed -i 's/buildenv/mysql${deploy_env}/g' mysql.yaml
            sed -i 's/builddb/mysql${deploy_env}db/g' mysql.yaml
          """

        sh("kubectl apply -f namespace.yaml")
				
					sh """
				kubectl get secret -n ${deploy_namespace} >> nodcred.txt
				"""
			   sh '''
				if grep -q gcrcred nodcred.txt; then
				echo "gcrcred found " >> pullsecret.txt
				else
			    echo "gcrcred not found "		
				fi
				'''
                if (fileExists('pullsecret.txt'))
		  {
			   echo "gcrcred already exists"
		  }
		  else
		  {
			  echo "gcrcred does not exists"
			   withCredentials([[$class: 'UsernamePasswordMultiBinding',
                credentialsId: 'gitlab',
                usernameVariable: 'DOCKER_HUB_USER',
                passwordVariable: 'DOCKER_HUB_PASSWORD']]) {
					
					sh """
					kubectl create secret docker-registry gcrcred --docker-server=localhost:32121 --docker-username=${DOCKER_HUB_USER} --docker-password=${DOCKER_HUB_PASSWORD} -n ${deploy_namespace}
          
					"""
				}
		  }	
                  try{
                    sh("kubectl get deployment/mysql${deploy_env} -n ${deploy_namespace}")
                    if(true)
                    {
                      echo "mysql exits"
                    }
                  }
                  catch(e)
                  {
                    sh("echo Deploy Namespace: ${deploy_namespace}")
                  sh '''
                    kubectl apply -f mysql-pvc.yaml
                    kubectl apply -f mysql.yaml
					          sleep 120
                    echo "Deploy Namespace: ${deploy_namespace}"
                    '''
				            POD=sh (returnStdout: true, script: """kubectl get pod -l app=mysqldev -n ${deploy_namespace} -o jsonpath="{.items[0].metadata.name}" """)
					          echo "${POD}"
					          sh ("kubectl exec -i ${POD} -n ${deploy_namespace} -- mysql -u root -proot < customer-product.sql")
                  
                  }
				try{
				
				sh ("kubectl get deployment/microsvc${deploy_env} -n ${deploy_namespace}")
				if(true)
				{
					sh """
            kubectl delete deployment microsvcdev -n ${deploy_namespace}
				    kubectl apply -f tomcat.yaml
					"""
				}
				}
				catch(e)
				{
                sh("kubectl apply -f tomcat.yaml")
				sh("kubectl apply -f tomcat-svc.yaml")
                  
				sh 'sleep 250'
				sh ("kubectl get svc microsvc${deploy_env} -n ${deploy_namespace}")
				}
				LB = sh (returnStdout: true, script: """ kubectl get svc microsvcdev -n ${deploy_namespace} -o jsonpath="{ .status.loadBalancer.ingress[*]['ip', 'hostname']}" """)
				 echo "LB: ${LB}"
				def loadbalancer = "http://"+LB
				echo "application_url: ${loadbalancer}:8080/"
				}
			}
		}
        stage('function testing')
		{
			echo "functional testing"
			
		}
        stage('deploy to staging')
		{
            echo "Deploy staging successful "
        }
        stage('deploy to production')
		{
            echo "Deploy Production approved and successful "
        }

        }       
        catch (Exception err) 
        {
            currentBuild.result = 'FAILURE'
            echo 'Exception occurred: ' + err.toString()
            echo "RESULT: ${currentBuild.result}"
            echo "Finished: ${currentBuild.result}"
        } 
    }
}  
}  


podTemplate(label: cleanuplabel, serviceAccount: serviceaccount, containers: [
  containerTemplate(name: 'kubectl', image: kubectl_image, ttyEnabled: true, command: 'cat')], imagePullSecrets: ['gcrcred']) {

  node(cleanuplabel) {
    stage('Post-build-CleanUp') 
    {         
      container('kubectl') 
      { 
        cleanup(label)
      }    
    }
  }
}
