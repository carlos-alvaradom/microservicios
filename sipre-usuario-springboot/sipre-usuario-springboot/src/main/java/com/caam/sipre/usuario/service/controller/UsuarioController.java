package com.caam.sipre.usuario.service.controller;

import com.caam.sipre.usuario.service.entity.Usuario;
import com.caam.sipre.usuario.service.UsuarioService;
import com.caam.sipre.usuario.service.model.CarroModel;
import com.caam.sipre.usuario.service.model.MotoModel;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/carro/{usarioId}")
    public ResponseEntity<List<CarroModel>> obtenerCarros(@PathVariable("usarioId") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        List<CarroModel> carros = usuarioService.getCarros(id);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/moto/{usarioId}")
    public ResponseEntity<List<MotoModel>> obtenerMotos(@PathVariable("usarioId") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        List<MotoModel> motos = usuarioService.getMotos(id);
        return ResponseEntity.ok(motos);
    }

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

    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<MotoModel> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody MotoModel moto){
        MotoModel nuevaMoto = usuarioService.saveMoto(usuarioId,moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @GetMapping("/all/{usuarioId}")
    public ResponseEntity <Map<String,Object>> listarTodosVehiculos(@PathVariable("usuarioId") int usuarioId){
        Map<String,Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }
}
