package com.ouag.demo.aksutils.service;


import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Pod;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ManagementService {

    private CoreV1Api coreV1Api;
    private AppsV1Api appsV1Api;

    public boolean restartPod(String podName, String namespace) throws ApiException {
        log.info("Restarting pod name {} and namespace {}", podName, namespace);
        this.deletePod(podName, namespace);
        return true;
    }

    public boolean deletePod(String podName, String namespace) throws ApiException {
        log.info("Deleting pod name {} and namespace {}", podName, namespace);
        V1Pod pod = coreV1Api.deleteNamespacedPod(podName, namespace, null, null, null, null, null, null);
        return true;
    }

    public boolean scaleDeployment(String deploymentName, String namespace, int replicas) throws ApiException {
        log.info("Scaling deployment name {} and namespace {} with replicas {}", deploymentName, namespace, replicas);
        V1Patch patch = new V1Patch("[{\"op\":\"replace\",\"path\":\"/spec/replicas\",\"value\":"+replicas+"}]");
        V1Deployment deplist = appsV1Api.patchNamespacedDeployment(deploymentName,namespace, patch, null, null, null, null);
        log.info("deployment {} scaled.", deploymentName);
        return true;
    }
}
