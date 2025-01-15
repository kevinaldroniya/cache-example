package com.example.cache.service;

import com.example.cache.model.General;
import com.example.cache.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class GeneralService {

//    private static final String REDIS_KEY = "general_data";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveData(Map<String, Object> general){
        general.forEach((serviceName, serviceData) -> {
            String redisKey = getRedisKeyForService(serviceName);
            redisTemplate.opsForValue().set(redisKey, serviceData);  // Save service data directly
        });
    }

    public Map<String, Object> getAllData() {
        Set<String> keys = redisTemplate.keys("service_data:*"); // Get all keys with the pattern "service_data:*"
        Map<String, Object> allData = new HashMap<>();

        if (keys != null) {
            for (String key : keys) {
                String serviceName = key.replace("service_data:", "");  // Extract service name from key
                Object serviceData = redisTemplate.opsForValue().get(key);
                allData.put(serviceName, serviceData);  // Put the service data directly in the map
            }
        }
        return allData;
    }

    public Object getValueByPath(String keyPath){
        String[] pathParts = keyPath.split("\\.");  // Split path into parts by "."
        if (pathParts.length == 0) {
            return null;
        }

        String serviceName = pathParts[0];  // The first part is the service name
        String redisKey = getRedisKeyForService(serviceName);  // Get the Redis key for this service

        Object serviceData = redisTemplate.opsForValue().get(redisKey);
        if (serviceData == null) {
            return null;  // If the service doesn't exist in Redis, return null
        }

        // Traverse the path to find the nested key
        for (int i = 1; i < pathParts.length; i++) {
            if (serviceData instanceof Map) {
                serviceData = ((Map<?, ?>) serviceData).get(pathParts[i]);
            } else {
                return null;  // If the data is not a Map, path traversal fails
            }
        }

        return serviceData;
    }

    // Generate Redis key based on service name
    private String getRedisKeyForService(String serviceName) {
        return "service_data:" + serviceName;
    }
}
