### Microservicio Gestion de Cursos

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void testObtenerCursoPorId() {
        Curso curso = new Curso(1L, "Spring Boot", "Desarrollo con Spring");
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Curso resultado = cursoService.obtenerCursoPorId(1L);

        assertNotNull(resultado);
        assertEquals("Spring Boot", resultado.getNombre());
    }
}