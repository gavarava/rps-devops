# Deployment using k8s

`kubectl create -f namespace-dev.json`
`kubectl apply -f service.yml`

- Expose
`kubectl expose deployment/rps-deployment`
`kubectl expose service/rps-app --namespace development`
- Get pods
  `kubectl get pods --namespace development`
  
- Describe Pod - To check what failed with this POD
  `kubectl describe pod rps-application-75f475c44d-bpzr7 --namespace development`
- Get services
`kubectl get services`
- Expose services
`kubectl expose deployment/rps-application --namespace development`
`kubectl expose service/rps-app --namespace=development --type=LoadBalancer --name=rps-service`
- Get Pod IP ADDRESS
`kubectl get pods -l run=rps-app -o yaml | grep podIP`
  `kubectl get pods --namespace development -l run=rps-app -o yaml | grep podIP`
- Get Pod
  `kubectl get pods --output=wide`
- Get all deployments
  `kubectl get deployments --all-namespaces`
  
- WARNING delete
  `kubectl delete --all services --all-namespaces`
  `kubectl delete deployment.apps/rps-deployment --namespace development`
  `kubectl exec -ti dnsutils --nslookup rps-app`
---
`
gauravedekar@Gauravs-MBP deployment % kubectl get svc --namespace=development
NAME              TYPE           CLUSTER-IP       EXTERNAL-IP   PORT(S)             AGE
rps-application   ClusterIP      10.106.217.189   <none>        8080/TCP,8091/TCP   16m
rps-deployment    ClusterIP      10.109.88.147    <none>        8080/TCP,8091/TCP   4h52m
rps-service       LoadBalancer   10.104.197.110   localhost     80:32191/TCP        3h47m
`



- What is difference b/w Service - deployment and POD ?
- How to expose port of a pod ?
  - https://kubernetes.io/docs/concepts/services-networking/service/
    To get stuff out in the open use a service
- Does every pod get a domain name ?