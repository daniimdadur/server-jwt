package spring.security.practice.master.fakultas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.security.practice.base.BaseController;
import spring.security.practice.base.Response;
import spring.security.practice.master.fakultas.model.FakultasRes;
import spring.security.practice.master.fakultas.service.FakultasService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fakultas")
public class FakultasController extends BaseController<FakultasRes> {
    private final FakultasService fakultasService;

    @GetMapping
    public ResponseEntity<Response> get() {
        List<FakultasRes> result = this.fakultasService.get();
        return getResponse(result);
    }
}
