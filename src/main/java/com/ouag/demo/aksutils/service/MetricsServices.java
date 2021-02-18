package com.ouag.demo.aksutils.service;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MetricsServices {

    private CoreV1Api coreV1Api;


}
