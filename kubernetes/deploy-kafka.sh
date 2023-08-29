cd kubernetes
kubectl delete -f deployment/zookeeper-deployment.yaml
kubectl delete -f deployment/kafka-deployment.yaml

kubectl create -f deployment/zookeeper-deployment.yaml
kubectl create -f deployment/kafka-deployment.yaml

kubectl apply -f deployment/zookeeper-deployment.yaml
kubectl apply -f deployment/kafka-deployment.yaml

# Check that the pods are running
kubectl get pods
cd ..