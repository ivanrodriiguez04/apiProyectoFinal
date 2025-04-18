package Api.proyectoFinalDWSDIW.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.SucursalDao;
import Api.proyectoFinalDWSDIW.dtos.SucursalDto;
import Api.proyectoFinalDWSDIW.repositorios.SucursalRepositorio;

@Service
public class SucursalServicio {

    @Autowired
    private SucursalRepositorio sucursalRepositorio;

    public SucursalDto guardarSucursal(SucursalDto sucursalDto) {
        SucursalDao sucursal = new SucursalDao();
        sucursal.setCiudadSucursal(sucursalDto.getCiudadSucursal());
        sucursal.setCodigoBanco(sucursalDto.getCodigoBanco());
        sucursal.setCodigoSucursal(sucursalDto.getCodigoSucursal());
        sucursal.setDireccionSucursal(sucursalDto.getDireccionSucursal());

        sucursal = sucursalRepositorio.save(sucursal);
        return new SucursalDto(
            sucursal.getIdSucursal(), 
            sucursal.getCiudadSucursal(), 
            sucursal.getCodigoBanco(), 
            sucursal.getCodigoSucursal(), 
            sucursal.getDireccionSucursal()
        );
    }

    public List<SucursalDto> obtenerTodas() {
        return sucursalRepositorio.findAll().stream()
            .map(s -> new SucursalDto(s.getIdSucursal(), s.getCiudadSucursal(), s.getCodigoBanco(), s.getCodigoSucursal(), s.getDireccionSucursal()))
            .collect(Collectors.toList());
    }
}
