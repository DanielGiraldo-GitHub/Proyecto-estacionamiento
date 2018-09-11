package co.com.ceiba.estacionamiento.unitaria;


import javax.persistence.EntityManager;

public class TestVehiculoRepositoryImpl {

	private static EntityManager entityManager = null;

	static final String EXITO_AL_GUARDAR_VEHICULO = "Exito";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";
/*
	@BeforeClass
	public static void setUpClass() throws Exception {
		if (entityManager == null) {
			entityManager = (EntityManager) Persistence.createEntityManagerFactory("TestPersistence")
					.createEntityManager();
		}
	}

	@Test
	public void guardarVehiculoTest() {

		
		// arrange
		Vehiculo vehiculo = new Vehiculo();
		
		vehiculo.setPlaca("BCA54C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);

		VehiculoRepositoryImpl vehiculoRepositoryImpl = mock(VehiculoRepositoryImpl.class);
		// act
		vehiculoRepositoryImpl.entityManager = entityManager;
		entityManager.getTransaction().begin();
		
		RestResponse response = vehiculoRepositoryImpl.guardarVehiculo(vehiculo);

		RestResponse respuestaEsperada = new RestResponse(HttpStatus.OK.value(), EXITO_AL_GUARDAR_VEHICULO);
		// assert
		Assert.assertEquals(response.getMessage(), respuestaEsperada.getMessage());

		entityManager.remove(vehiculo);
		entityManager.getTransaction().commit();
	}

	@Test
	public void guardarVehiculoSinPlacaTest() {

		// arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);

		VehiculoRepositoryImpl vehiculoRepositoryImpl = mock(VehiculoRepositoryImpl.class);

		// act
		RestResponse response = vehiculoRepositoryImpl.guardarVehiculo(vehiculo);

		RestResponse respuestaEsperada = new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), CAMPOS_SIN_DILIGENCIAR);
		// assert
		Assert.assertEquals(response.getMessage(), respuestaEsperada.getMessage());
	}
	
	 * @Test public Object buscarVehiculo() {
	 * 
	 * String sentencia = "SELECT v FROM Vehiculo v WHERE v.placa ='" + placa +
	 * "'"; Query query = entityManager.createQuery(sentencia); return
	 * (query.getSingleResult()); }
	 * 
	 * @Test public void esPrestadoTest() {
	 * 
	 * // arrange LibroTestDataBuilder libroTestDataBuilder = new
	 * LibroTestDataBuilder();
	 * 
	 * Libro libro = libroTestDataBuilder.build();
	 * 
	 * RepositorioPrestamo repositorioPrestamo =
	 * mock(RepositorioPrestamo.class); RepositorioLibro repositorioLibro =
	 * mock(RepositorioLibro.class);
	 * 
	 * when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).
	 * thenReturn(libro);
	 * 
	 * Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro,
	 * repositorioPrestamo);
	 * 
	 * // act boolean esPrestado = bibliotecario.esPrestado(libro.getIsbn());
	 * 
	 * // assert assertTrue(esPrestado); }
	 * 
	 * @Test public void libroNoPrestadoTest() {
	 * 
	 * // arrange LibroTestDataBuilder libroTestDataBuilder = new
	 * LibroTestDataBuilder();
	 * 
	 * Libro libro = libroTestDataBuilder.build();
	 * 
	 * RepositorioPrestamo repositorioPrestamo =
	 * mock(RepositorioPrestamo.class); RepositorioLibro repositorioLibro =
	 * mock(RepositorioLibro.class);
	 * 
	 * when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).
	 * thenReturn(null);
	 * 
	 * Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro,
	 * repositorioPrestamo);
	 * 
	 * // act boolean esPrestado = bibliotecario.esPrestado(libro.getIsbn());
	 * 
	 * // assert assertFalse(esPrestado); }
	 * 
	 * @Test public void libroPrestadoTest() {
	 * 
	 * // arrange LibroTestDataBuilder libroTestDataBuilder = new
	 * LibroTestDataBuilder();
	 * 
	 * Libro libro = libroTestDataBuilder.build();
	 * 
	 * RepositorioPrestamo repositorioPrestamo =
	 * mock(RepositorioPrestamo.class); RepositorioLibro repositorioLibro =
	 * mock(RepositorioLibro.class);
	 * 
	 * when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).
	 * thenReturn(null);
	 * 
	 * Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro,
	 * repositorioPrestamo);
	 * 
	 * // act boolean esPrestado = bibliotecario.esPrestado(libro.getIsbn());
	 * 
	 * // assert assertFalse(esPrestado); }
	 * 
	 * @Test public void noEsPalindromoTest() {
	 * 
	 * // arrange LibroTestDataBuilder libroTestDataBuilder = new
	 * LibroTestDataBuilder();
	 * 
	 * Libro libro = libroTestDataBuilder.build();
	 * 
	 * RepositorioPrestamo repositorioPrestamo =
	 * mock(RepositorioPrestamo.class); RepositorioLibro repositorioLibro =
	 * mock(RepositorioLibro.class);
	 * 
	 * when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).
	 * thenReturn(libro);
	 * 
	 * Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro,
	 * repositorioPrestamo);
	 * 
	 * // act boolean esPalindromo =
	 * bibliotecario.esPalindromo(libro.getIsbn());
	 * 
	 * // assert assertFalse(esPalindromo); }
	 * 
	 * @AfterClass public static void afterClass() { try {
	 * entityManager.clear();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */
}
