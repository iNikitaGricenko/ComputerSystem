### set docker env
eval $(minikube docker-env)

kubectl config set-context $(kubectl config current-context) --namespace=default

### build the repository
mvn clean package

### build the docker images on minikube

### Secret
kubectl delete -f kubernetes/secret/reg-pod.yaml
kubectl create -f kubernetes/secret/reg-pod.yaml
kubectl apply -f kubernetes/secret/reg-pod.yaml
kubectl get pod private-reg

### elastic/kibana
kubernetes/deploy-elastic.sh

### mongodb/secret
kubernetes/deploy-mongo.sh

### mysql/secret
kubernetes/deploy-mysql.sh

### redis
kubernetes/deploy-redis.sh

### redis
kubernetes/deploy-kafka.sh

### application
kubectl delete -f kubernetes/config/kubernetes-config.yaml
kubectl create -f kubernetes/config/kubernetes-config.yaml

# Check that the pods are running
kubectl get pods

#kubectl describe pod customer-deployment
#kubectl describe pod product-service