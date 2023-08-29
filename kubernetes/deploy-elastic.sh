### elastic/kibana
cd kubernetes

kubectl delete -f deployment/elastic-main-deployment.yaml
kubectl delete -f deployment/elastic-second-deployment.yaml
kubectl delete -f deployment/kibana-deployment.yaml

kubectl create -f deployment/elastic-main-deployment.yaml
kubectl create -f deployment/elastic-second-deployment.yaml
kubectl create -f deployment/kibana-deployment.yaml

kubectl apply -f deployment/elastic-main-deployment.yaml
kubectl apply -f deployment/elastic-second-deployment.yaml
kubectl apply -f deployment/kibana-deployment.yaml

# Check that the pods are running
kubectl get pods
cd ..