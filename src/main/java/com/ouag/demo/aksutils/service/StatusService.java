package com.ouag.demo.aksutils.service;

import io.kubernetes.client.extended.kubectl.exception.KubectlException;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class StatusService {

    private CoreV1Api coreV1Api;
    private AppsV1Api appsV1Api;

    public Collection<AbstractMap.SimpleImmutableEntry> getPods(String namespace) throws ApiException, KubectlException {
        log.info("Getting Pods namespaced with {} ", namespace);
        V1PodList podList = coreV1Api.listNamespacedPod(namespace,null, null, null, null, null, null, null, null,null);
        return podList.getItems().stream().map(item -> new AbstractMap.SimpleImmutableEntry<>(item.getMetadata().getName(), item.getStatus())).collect(Collectors.toSet());
    }

    public Collection<String> getDeployment(String namespace) throws ApiException {
        log.info("Getting deployment namespaced with {} ", namespace);
        V1DeploymentList deploymentList = appsV1Api.listNamespacedDeployment(namespace,null, null, null, null, null, null, null, null,null);
        return deploymentList.getItems().stream().map(item -> item.getMetadata().getName()).collect(Collectors.toSet());
    }
}
