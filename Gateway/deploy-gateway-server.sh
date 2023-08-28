### set docker env
eval $(minikube docker-env)

### build the repository
mvn clean install

### build the docker images on minikube
docker build -t gateway .

### travel-agency-service
kubectl delete -f ./gateway-deployment.yaml
kubectl create -f ./gateway-deployment.yaml

# Check that the pods are running
kubectl get pods