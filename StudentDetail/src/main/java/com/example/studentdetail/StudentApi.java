package com.example.studentdetail;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value ="student", url = "http://localhost:8081")
public interface StudentApi {

    @GetMapping("/firat-university")
    List<String> getFindByFiratUniversity();

    @GetMapping("/istanbul-university")
    List<String> getFindByIstanbulUniversity();
}
