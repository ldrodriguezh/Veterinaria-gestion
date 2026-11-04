package co.edu.ucompensar.veterinaria;

import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.repository.MascotaRepository;
import co.edu.ucompensar.veterinaria.service.MascotaService;
import co.edu.ucompensar.veterinaria.service.MascotaServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VeterinariaApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext Context = SpringApplication.run(VeterinariaApplication.class, args);

        MascotaService mascotaService = Context.getBean(MascotaServiceImpl.class);

        Mascota mascota = new Mascota();
        mascota.setNombre("Mascota 1");
        mascota.setEspecie("Bulldog");
        mascota.setNombrePropietario("Marco");
        mascota.setTelefonoPropietario("+573144440004");

        //mascotaService.registrar(mascota);
    }

}
