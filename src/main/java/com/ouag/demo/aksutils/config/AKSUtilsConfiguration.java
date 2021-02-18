package com.ouag.demo.aksutils.config;


import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@org.springframework.context.annotation.Configuration
@Slf4j
public class AKSUtilsConfiguration {

    @Bean
    public ApiClient buildApiClient() throws IOException{
        // file path to your KubeConfig
        String kubeConfigPath = "*";

        // loading the out-of-cluster config, a kubeconfig from file-system
        ApiClient client =
                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath)))
                        .setVerifyingSsl(false).build();


        // set the global default api-client to the in-cluster one from above
        Configuration.setDefaultApiClient(client);
        client.setDebugging(true);
        return client;
    }

    @Bean
    public CoreV1Api getCoreV1Api(ApiClient client)  {
        return new CoreV1Api(client);
    }

    @Bean
    public AppsV1Api getAppsV1Api(ApiClient client) {
        return new AppsV1Api(client);
    }
}
