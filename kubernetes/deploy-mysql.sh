cd kubernetes
kubectl delete -f secret/secret.yaml
kubectl delete -f deployment/mysql-deployment.yaml

kubectl create -f secret/secret.yaml
kubectl create -f deployment/mysql-deployment.yaml

kubectl apply -f secret/secret.yaml
kubectl apply -f deployment/mysql-deployment.yaml

# Check that the pods are running
kubectl get pods
cd ..