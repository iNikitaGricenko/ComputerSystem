cd kubernetes
kubectl delete -f deployment/redis-deployment.yaml
kubectl create -f deployment/redis-deployment.yaml

kubectl apply -f deployment/redis-deployment.yaml

# Check that the pods are running
kubectl get pods
cd ..