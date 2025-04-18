package Api.proyectoFinalDWSDIW.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.SucursalDto;
import Api.proyectoFinalDWSDIW.servicios.SucursalServicio;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalControlador {

    @Autowired
    private SucursalServicio sucursalServicio;

    @PostMapping
    public ResponseEntity<SucursalDto> crearSucursal(@RequestBody SucursalDto sucursalDto) {
        SucursalDto nuevaSucursal = sucursalServicio.guardarSucursal(sucursalDto);
        return new ResponseEntity<>(nuevaSucursal, HttpStatus.CREATED);
    }

    @GetMapping
    public List<SucursalDto> obtenerSucursales() {
        return sucursalServicio.obtenerTodas();
    }
}