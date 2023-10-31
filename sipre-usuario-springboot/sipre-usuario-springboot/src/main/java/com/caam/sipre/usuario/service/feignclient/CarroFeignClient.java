package com.caam.sipre.usuario.service.feignclient;

import com.caam.sipre.usuario.service.model.CarroModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "carro-service")
@RequestMapping("/carro")
public interface CarroFeignClient {

    @PostMapping()
    public CarroModel save(@RequestBody CarroModel carro);

    @GetMapping("/usuario/{usuarioId}")
    public List<CarroModel> getCarros(@PathVariable("usuarioId") int usuarioId);

    /*@GetMapping("{id}")
    public CarroModel getCarroById(@PathVariable("id") int id);*/
}
