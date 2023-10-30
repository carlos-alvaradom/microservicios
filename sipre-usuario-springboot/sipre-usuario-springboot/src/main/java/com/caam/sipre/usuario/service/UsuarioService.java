package com.caam.sipre.usuario.service;

import com.caam.sipre.usuario.service.entity.Usuario;
import com.caam.sipre.usuario.service.feignclient.CarroFeignClient;
import com.caam.sipre.usuario.service.feignclient.MotoFeignClient;
import com.caam.sipre.usuario.service.model.CarroModel;
import com.caam.sipre.usuario.service.model.MotoModel;
import com.caam.sipre.usuario.service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    public CarroModel saveCarro(int usuarioId, CarroModel carro){
        carro.setUsuarioId(usuarioId);
        CarroModel nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }

    /*public CarroModel getCarroById(int id){
        CarroModel carro = carroFeignClient.getCarroById(id);
        return carro;
    }*/

    public MotoModel saveMoto(int usuarioId, MotoModel moto){
        moto.setUsuarioId(usuarioId);
        MotoModel nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<CarroModel> getCarros(int usuarioId){
        List<CarroModel> carros = restTemplate.getForObject("http://localhost:8180/carro/usuario/" + usuarioId, List.class);
        return carros;
    }

    public List<MotoModel> getMotos(int usuarioId){
        List<MotoModel> motos = restTemplate.getForObject("http://localhost:8280/moto/usuario/" + usuarioId, List.class);
        return motos;
    }

    public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
        Map<String,Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if(usuario == null){
            resultado.put("Mensaje","Usuario no Entocntrado");
            return resultado;
        }
        resultado.put("Usuario",usuario);
        List<CarroModel> carros = carroFeignClient.getCarros(usuarioId);
        if(carros.isEmpty()){
            resultado.put("Carros","El Usuario No tiene Carros");
        }else{
            resultado.put("Carros",carros);
        }
        List<MotoModel> motos = motoFeignClient.getMotos(usuarioId);
        if(motos.isEmpty()){
            resultado.put("motos","El Usuario No tiene Motos");
        }else{
            resultado.put("Motos", motos);
        }
        return resultado;
    }
}
