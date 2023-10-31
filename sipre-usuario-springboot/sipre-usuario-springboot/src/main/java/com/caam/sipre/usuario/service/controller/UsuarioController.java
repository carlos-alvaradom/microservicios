package com.caam.sipre.usuario.service.controller;

import com.caam.sipre.usuario.service.entity.Usuario;
import com.caam.sipre.usuario.service.UsuarioService;
import com.caam.sipre.usuario.service.model.CarroModel;
import com.caam.sipre.usuario.service.model.MotoModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getAll();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "obtenerCarrosFBM")
    @GetMapping("/carro/{usarioId}")
    public ResponseEntity<List<CarroModel>> obtenerCarros(@PathVariable("usarioId") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        List<CarroModel> carros = usuarioService.getCarros(id);
        return ResponseEntity.ok(carros);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "obtenerMotosFBM")
    @GetMapping("/moto/{usarioId}")
    public ResponseEntity<List<MotoModel>> obtenerMotos(@PathVariable("usarioId") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        List<MotoModel> motos = usuarioService.getMotos(id);
        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "guardarCarroFBM")
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<CarroModel> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody CarroModel carro){
        CarroModel nuevoCarro = usuarioService.saveCarro(usuarioId,carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    /*@GetMapping("/carro-id/{id}")
    public ResponseEntity<CarroModel> obtenerCarro(@PathVariable("id") int id){
        CarroModel carro = usuarioService.getCarroById(id);
        return ResponseEntity.ok(carro);
    }*/

    @CircuitBreaker(name = "motosCB", fallbackMethod = "guardarMotoFBM")
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<MotoModel> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody MotoModel moto){
        MotoModel nuevaMoto = usuarioService.saveMoto(usuarioId,moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "todosFBM")
    @GetMapping("/all/{usuarioId}")
    public ResponseEntity <Map<String,Object>> listarTodosVehiculos(@PathVariable("usuarioId") int usuarioId){
        Map<String,Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }

    private ResponseEntity<CarroModel> obtenerCarrosFBM(@PathVariable("usuarioId") int id, RuntimeException e){
        return new ResponseEntity("El usuario :" + id + " Tiene los carros en el taller", HttpStatus.OK);
    }
    private ResponseEntity<List<CarroModel>> guardarCarroFBM(@PathVariable("usuarioId") int id, @RequestBody CarroModel carro, RuntimeException e){
        return new ResponseEntity("El usuario :" + id + " No Tiene dinero para los carros", HttpStatus.OK);
    }

    private ResponseEntity<MotoModel> obtenerMotosFBM(@PathVariable("usuarioId") int id, RuntimeException e){
        return new ResponseEntity("El usuario :" + id + " Tiene los motos en el taller", HttpStatus.OK);
    }
    private ResponseEntity<List<MotoModel>> guardarMotoFBM(@PathVariable("usuarioId") int id,@RequestBody MotoModel moto, RuntimeException e){
        return new ResponseEntity("El usuario :" + id + " No Tiene dinero para las motos", HttpStatus.OK);
    }

    public ResponseEntity <Map<String,Object>> todosFBM(@PathVariable("usuarioId") int id, RuntimeException e){
        return new ResponseEntity("El usuario :" + id + " Tiene los vehiculos en el taller", HttpStatus.OK);
    }
}
