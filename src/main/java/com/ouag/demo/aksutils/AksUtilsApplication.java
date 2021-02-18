package com.ouag.demo.aksutils;

import com.ouag.demo.aksutils.service.ManagementService;
import com.ouag.demo.aksutils.service.StatusService;
import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.extended.kubectl.Kubectl;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileReader;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@Slf4j
public class AksUtilsApplication {

	@Autowired
	StatusService statusService;

	@Autowired
	ManagementService mgtService;

	public static void main(String[] args) {
		SpringApplication.run(AksUtilsApplication.class, args);
	}

	@Bean
	public ApplicationRunner getRunner(){
		return (args) -> {
			Collection list = statusService.getPods("default");
			log.info("pods {}", list.size());
			mgtService.scaleDeployment("kubia-deployment", "default", 2);
			log.info("pods {}", list.size());
		};
	}
	/*
	@Bean
	public ApplicationRunner getRunner(){
		return (args) -> {

			log.info("Runner starting ....");
			// file path to your KubeConfig
			String kubeConfigPath = "/Users/mouag/.kube/config";

			// loading the out-of-cluster config, a kubeconfig from file-system
			ApiClient client =
					ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath)))
							.setVerifyingSsl(false).build();


			// set the global default api-client to the in-cluster one from above
			Configuration.setDefaultApiClient(client);
			// the CoreV1Api loads default api-client from global configuration.
			CoreV1Api api = new CoreV1Api();
			AppsV1Api appsApi = new AppsV1Api();


			log.info("getting nodes ....");
			V1Patch patch = new V1Patch("{\"op\":\"replace\",\"path\":\"/spec/replicas\",\"value\":\"3\"}");
			V1Deployment deplist = appsApi.patchNamespacedDeployment("deploymentname","default", patch, null, null, null, null);


			// invokes the CoreV1Api client
			V1PodList list =
					api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);

			log.info("{} pods found.", list.getItems().size());
			list.getItems().stream().forEach(item ->
			{
				log.info(item.getMetadata().getName());

			});


			log.info("getting namespaces ....");

			V1NamespaceList nslist =
					api.listNamespace(null, null, null, null, null, null, null, null, null);

			log.info("{} namespaces found.", nslist.getItems().size());
			nslist.getItems().stream().forEach(item -> log.info(item.getMetadata().getName()));
		};
	}*/

}
