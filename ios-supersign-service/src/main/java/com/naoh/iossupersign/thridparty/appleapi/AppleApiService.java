package com.naoh.iossupersign.thridparty.appleapi;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.naoh.iossupersign.enums.AppleApiEnum;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleApiResult;
import com.naoh.iossupersign.model.dto.AppleDeviceDTO;
import com.naoh.iossupersign.model.dto.AppleReqBody;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppleApiService extends AppleApi{

//    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 取得帳號下的設備資訊
     * @param authorizeBO
     * @return
     */
    public List<AppleDeviceDTO> getNumberOfAvailableDevices(AuthorizeBO authorizeBO) {
        HttpHeaders headers = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = AppleApiEnum.LIST_DEVICE_API.getApiPath();
        ResponseEntity<AppleApiResult<List<AppleDeviceDTO>>> response = restTemplate.exchange(url,AppleApiEnum.LIST_DEVICE_API.getHttpMethod(),httpEntity,
                new ParameterizedTypeReference<AppleApiResult<List<AppleDeviceDTO>>>(){});
        AppleApiResult<List<AppleDeviceDTO>> responseBody = response.getBody();
        return responseBody.getData();
    }

    public String registerNewDevice(String udid , AuthorizeBO authorizeBO){
        HttpHeaders headers = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());

        AppleReqBody attributes = AppleReqBody.init().add("name", udid).add("udid", udid).add("platform", "IOS");
        AppleReqBody body = AppleReqBody.init().add("type", "devices").add("attributes", attributes);
        AppleReqBody data = AppleReqBody.init().add("data",body);
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(data,headers);
        String url = AppleApiEnum.LIST_DEVICE_API.getApiPath();

        ResponseEntity<String> response = restTemplate.exchange(url,AppleApiEnum.LIST_DEVICE_API.getHttpMethod(),httpEntity,
                String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }





}
