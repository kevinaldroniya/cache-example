package com.example.cache.controller;

import com.example.cache.model.General;
import com.example.cache.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
public class GeneralController {

    @Autowired
    private GeneralService generalService;

    @PostMapping
    public String saveData(@RequestBody Map<String, Object> general){
        generalService.saveData(general);
        return "Data saved";
    }

    @GetMapping
    public Object getAll(){
        return generalService.getAllData();
    }

    @GetMapping("/{keyPath}")
    public Object getDataByPath(@PathVariable("keyPath") String keyPath){
        Object data = generalService.getValueByPath(keyPath);

        if (data != null) {
            Map<String, Object> response = new HashMap<>();
            response.put(keyPath, data);
            return ResponseEntity.ok(response);  // Return as JSON with pathKey as the dynamic key
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Data not found for path: " + keyPath));
        }
    }
}
