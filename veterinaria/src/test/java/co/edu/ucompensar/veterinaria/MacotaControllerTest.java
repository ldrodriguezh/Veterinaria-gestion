package co.edu.ucompensar.veterinaria;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.edu.ucompensar.veterinaria.Exception.RecursoNoEncontradoException;
import co.edu.ucompensar.veterinaria.Exception.ValidacionException;
import co.edu.ucompensar.veterinaria.model.Cita;
import co.edu.ucompensar.veterinaria.model.Mascota;
import co.edu.ucompensar.veterinaria.service.MascotaService;
import co.edu.ucompensar.veterinaria.controller.MascotaController;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(MascotaController.class)
public class MascotaControllerTest {

    private static final String JSON_NUEVA = """
            {"nombre":"Luna","especie":"Perro","raza":"Criollo",
             "fechaNacimiento":"2022-03-10",
             "nombrePropietario":"Ana Pérez","telefonoPropietario":"3001234567"}
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaService mascotaService;

    // --------- RF-01 Registrar ---------

    @Test
    @DisplayName("POST /api/mascotas válido -> 201, Location y JSON con id")
    void registrar_valida_devuelve201() throws Exception {
        when(mascotaService.registrar(any(Mascota.class))).thenReturn(mascota(1L,"Luna"));

        mockMvc.perform(post("/api/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_NUEVA))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/mascotas/1")))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Luna"));
    }
}
