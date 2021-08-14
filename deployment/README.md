# Deployment using k8s

`kubectl create -f namespace-dev.json`</br>
`kubectl apply -f service.yml`</br>
- Cluster
  Health Check: `kubectl cluster-info`
- Node Commands
NODE STATUS: `kubectl get nodes`</br>
NODE DETAILS: `kubectl describe nodes`</br>
GET ReplicaSet: `kubectl get replicaset`

- Node Cordon / Uncordon
  `kubectl cordon docker-desktop` </br>
  `kubectl uncordon docker-desktop`
- Expose
`kubectl expose deployment/rps-deployment`</br>
`kubectl expose service/rps-app --namespace development`</br>
- Get pods
  `kubectl get all --namespace development`</br>
  `kubectl get all -n development`</br>
  `kubectl get pods --namespace development`</br>
  
- Describe Pod - To check what failed with this POD
  `kubectl describe pod rps-app-deployment-55bf9f757d-8hclt --namespace development`</br>
- Examining pod logs
  `kubectl logs ${POD_NAME} ${CONTAINER_NAME}`</br>
- Debugging with container exec
  `kubectl exec ${POD_NAME} -c ${CONTAINER_NAME} -- ${CMD} ${ARG1} ${ARG2} ... ${ARGN}`
- Check resources allocated
  `kubectl get resourcequota rps-app-deployment-55bf9f757d-w2tb5 -n development --output=yaml`
- Get services
`kubectl get services`
- Expose services
`kubectl expose deployment/rps-application --namespace development`</br>
`kubectl expose service/rps-app --namespace=development --type=LoadBalancer --name=rps-service`</br>
- Get Pod IP ADDRESS
`kubectl get pods -l run=rps-app -o yaml | grep podIP`</br>
`kubectl get pods --namespace development -l run=rps-app -o yaml | grep podIP`</br>
- Get Pod - wide information with IP Address
  `kubectl get pods --output=wide`
- Get all deployments
  `kubectl get deployments --all-namespaces`
  
- WARNING delete</br>
  `kubectl delete --all services --all-namespaces`</br>
  `kubectl delete deployment.apps/rps-deployment --namespace development`</br>
  `kubectl exec -ti dnsutils --nslookup rps-app`</br>
