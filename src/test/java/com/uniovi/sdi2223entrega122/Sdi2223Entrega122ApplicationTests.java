package com.uniovi.sdi2223entrega122;

import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.pageobjects.*;
import com.uniovi.sdi2223entrega122.services.InsertSampleDataService;
import com.uniovi.sdi2223entrega122.services.OffersService;
import com.uniovi.sdi2223entrega122.services.UsersService;
import com.uniovi.sdi2223entrega122.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Sdi2223Entrega122ApplicationTests {

    @Autowired
    OffersService offersService;
    @Autowired
    UsersService usersService;

    @Autowired
    private InsertSampleDataService insertSampleDataService;


    // CAMBIAR PATHFIREFOX Y GECKODRIVER AL NECESARIO PARA CADA UNO

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "E:\\Documentos\\UNIOVI\\Cuarto Curso\\SDI\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "D:\\usuario\\descargas\\sesion06\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\usuario\\OneDrive\\Escritorio\\Uni\\Tercero\\SDI\\Práctica\\Practica05\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "E:\\Documentos\\UNIOVI\\Cuarto Curso\\SDI\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\Usuario\\Downloads\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";

    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
    //static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";
    //Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";


    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(URL);
        usersService.deleteAll();
        insertSampleDataService.init();

    }

    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {
    }

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    // Registro de usuario con datos válidos
    @Test
    @Order(1)
    public void PR01() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_SignUpView.fillForm(driver, "miriam@email.com", "Miriam", "Gonzalez", "654321", "654321");
        // Comprobamos que nos redirige al home
        String checkText = "Mis Ofertas";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Registro de usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos).
    @Test
    @Order(2)
    public void PR02() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario. Email vacío
        PO_SignUpView.fillForm(driver, "", "Miriam", "Gonzalez", "654321", "654321");
        SeleniumUtils.textIsPresentOnPage(driver, "Este campo no puede ser vacío");
        // Rellenamos el formulario. Nombre vacío
        PO_SignUpView.fillForm(driver, "miriam@email.com", "", "Gonzalez", "654321", "654321");
        SeleniumUtils.textIsPresentOnPage(driver, "El nombre debe tener entre 3 y 24 caracteres.");

        // Rellenamos el formulario. Apellidos vacío
        PO_SignUpView.fillForm(driver, "miriam@email.com", "Miriam", "", "654321", "654321");
        SeleniumUtils.textIsPresentOnPage(driver, "El apellido debe tener entre 3 y 24 caracteres.");
        // Comprobamos que sigue en la página de signup
        String checkText = "Regístrate como usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // Registro de usuario con datos inválidos (repetición de contraseña inválida).
    @Test
    @Order(3)
    public void PR03() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_SignUpView.fillForm(driver, "miriam@email.com", "Miriam", "Gonzalez", "654321", "user01");
        // Comprobamos que se muestra el error de que no coinciden
        SeleniumUtils.textIsPresentOnPage(driver, "Las contraseñas no coinciden.");
        // Comprobamos que sigue en la página de signup
        String checkText = "Regístrate como usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // Registro de usuario con datos inválidos (email existente).
    @Test
    @Order(4)
    public void PR04() {
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_SignUpView.fillForm(driver, "user01@email.com", "Miriam", "Gonzalez", "654321", "user01");
        // Comprobamos que se muestra el error de que el email ya existe
        SeleniumUtils.textIsPresentOnPage(driver, "Este email ya existe.");
        // Comprobamos que sigue en la página de signup
        String checkText = "Regístrate como usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    // Inicio de sesión con datos válidos (administrador).
    @Test
    @Order(5)
    public void PR05() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Comprobamos que es el admin el que se logueo
        String checkText = "admin@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Comprobamos que hay una tabla con los campos email, nombre y apellidos
        checkText = "Email";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        checkText = "Nombre";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        checkText = "Apellidos";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // Hacemos logout
        PO_View.logout(driver);
    }

    // Inicio de sesión con datos válidos (usuario estándar).
    @Test
    @Order(6)
    public void PR06() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        // Comprobamos que es el usuario el que se logueo
        String checkText = "user01@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // Comprobamos que hay una tabla con los campos detalles y precio
        checkText = "Detalles";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        checkText = "Precio";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());

        // Hacemos logout
        PO_View.logout(driver);
    }

    // Inicio de sesión con datos inválidos (usuario estándar, campo email y contraseña vacíos).
    @Test
    @Order(7)
    public void PR07() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "", "");
        // Comprobamos que se muestra el error
        String checkText = "Credenciales incorrectas";
        SeleniumUtils.textIsPresentOnPage(driver, checkText);
        // Comprobamos que sigue en la página de login
        checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //  Inicio de sesión con datos válidos (usuario estándar, email existente, pero contraseña
    //incorrecta).
    @Test
    @Order(8)
    public void PR08() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "");
        // Comprobamos que se muestra el error
        String checkText = "Credenciales incorrectas";
        SeleniumUtils.textIsPresentOnPage(driver, checkText);
        // Comprobamos que sigue en la página de login
        checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //  Hacer clic en la opción de salir de sesión y comprobar que se redirige a la página de inicio de
    //sesión (Login).
    @Test
    @Order(9)
    public void PR09() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        // Hacemos logout
        PO_View.logout(driver);
        // Comprobamos que está en la página de login
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //  Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
    @Test
    @Order(10)
    public void PR10() {
        SeleniumUtils.textIsNotPresentOnPage(driver, "logout");
    }

    // Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el
    // sistema.
    @Test
    @Order(11)
    public void PR11() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario como admin
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Comprobamos que entramos como admin
        String checkText = "admin@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Comprobamos que se muestran todos los usuarios del sistema
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr");
        Assertions.assertEquals(result.size(), usersService.getUsers().size());
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza
    // y dicho usuario desaparece.
    @Test
    @Order(12)
    public void PR12() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario como admin
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Comprobamos que entramos como admin
        String checkText = "admin@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Obtenemos el número total de usuarios que hay ahora mismo
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr");
        int previousSize = result.size();
        // Vamos al listado de usuarios
        driver.navigate().to("http://localhost:8090/user/list");
        // Seleccionamos el primer usuario de la lista
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr/td/input");
        result.get(0).click();
        // Damos al botón borrar
        List<WebElement> botonBorrar = PO_View.checkElementBy(driver, "class", "btn");
        botonBorrar.get(1).click();
        // Comprobamos que el usuario user00@email.com ya no está
        checkText = "user00@email.com";
        SeleniumUtils.textIsNotPresentOnPage(driver, checkText);
        // Y que el tamaño de la lista redujo en uno
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr");
        int postSize = result.size();
        Assertions.assertEquals(postSize, previousSize - 1);
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza
    // y dicho usuario desaparece.
    @Test
    @Order(13)
    public void PR13() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario como admin
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Comprobamos que entramos como admin
        String checkText = "admin@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Obtenemos el número total de usuarios que hay ahora mismo
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr");
        int previousSize = result.size();
        // Vamos al listado de usuarios
        driver.navigate().to("http://localhost:8090/user/list");
        // Seleccionamos el ultimo usuario de la lista (ya que el ultimo era el admin pero añadimos uno en el signup)
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr/td/input");
        result.get(previousSize - 1).click();
        // Damos al botón borrar
        List<WebElement> botonBorrar = PO_View.checkElementBy(driver, "class", "btn");
        botonBorrar.get(1).click();
        // Comprobamos que el usuario miriam@email.com ya no está
        checkText = "miriam@email.com";
        SeleniumUtils.textIsNotPresentOnPage(driver, checkText);
        // Y que el tamaño de la lista redujo en uno
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr");
        int postSize = result.size();
        Assertions.assertEquals(postSize, previousSize - 1);
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se actualiza y dichos
    // usuarios desaparecen.
    @Test
    @Order(14)
    public void PR14() {
        // Vamos al formulario de logueo
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario como admin
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        // Comprobamos que entramos como admin
        String checkText = "admin@email.com";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Obtenemos el número total de usuarios que hay ahora mismo
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr");
        int previousSize = result.size();
        // Vamos al listado de usuarios
        driver.navigate().to("http://localhost:8090/user/list");
        // Seleccionamos los tres primeros usuarios de la lista
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr/td/input");
        result.get(2).click();
        result.get(4).click();
        result.get(5).click();
        // Damos al botón borrar
        List<WebElement> botonBorrar = PO_View.checkElementBy(driver, "class", "btn");
        botonBorrar.get(1).click();
        // Comprobamos que los usuarios ya no están
        SeleniumUtils.textIsNotPresentOnPage(driver, "user02@email.com");
        SeleniumUtils.textIsNotPresentOnPage(driver, "user04@email.com");
        SeleniumUtils.textIsNotPresentOnPage(driver, "user05@email.com");
        // Y que el tamaño de la lista redujo en uno
        result = PO_View.checkElementBy(driver, "free", "//table/tbody/tr");
        int postSize = result.size();
        Assertions.assertEquals(postSize, previousSize - 3);
        // Hacemos logout
        PO_View.logout(driver);
    }

    //Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar el botón Enviar.
    //Comprobar que la oferta sale en el listado de ofertas de dicho usuario.
    @Test
    @Order(15)
    public void PR015() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina
        driver.navigate().to("http://localhost:8090/offer/add");

        //Rellenamos el formulario
        PO_PrivateView.fillFormAddOffer(driver, "Coche rojo", "Coche de segunda mano en buen estado", 1000);

        //Comprobamos que aparece en el listado de ofertas del usuario
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, "text", "Coche de segunda mano en buen estado", 5);

        Assertions.assertEquals(elements.size(), 1);
    }

    // Ir al formulario de alta de oferta, rellenarla con datos inválidos (precio negativo) y pulsar el
    // botón Enviar. Comprobar que se muestra el mensaje de campo inválido.
    @Test
    @Order(16)
    public void PR016() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina
        driver.navigate().to("http://localhost:8090/offer/add");

        //Rellenamos el formulario
        PO_PrivateView.fillFormAddOffer(driver, "Coche rojo", "Coche de segunda mano en buen estado", -1000);

        //Comprobamos que aparece el mensaje de error
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, "text", "El precio debe ser mayor que 0", 5);

        Assertions.assertEquals(elements.size(), 1);
    }

    // Mostrar el listado de ofertas para dicho usuario y comprobar que se muestran todas los que
    //existen para este usuario
    @Test
    @Order(17)
    public void PR017() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina
        driver.navigate().to("http://localhost:8090/offer/list");
        //Comprobamos que aparecen todas sus ofertas
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        User user0 = usersService.getUserByEmail("user01@email.com");
        Assertions.assertEquals(offersService.getOffersForUser(user0).size(), elements.size());
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar que la lista se actualiza y
    // que la oferta desaparece.
    @Test
    @Order(18)
    public void PR018() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        //Voy a la pagina
        driver.navigate().to("http://localhost:8090/offer/list");

        // Cargamos la lista de ofertas
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, "text", "eliminar",
                PO_View.getTimeout());
        int numOffers = elements.size();

        // Eliminamos la primera oferta
        elements.get(0).click();

        //Comprobamos que se ha eliminado
        elements = SeleniumUtils.waitLoadElementsBy(driver, "text", "eliminar", PO_View.getTimeout());

        Assertions.assertEquals(numOffers - 1, elements.size());

    }

    // Ir a la lista de ofertas, borrar la última oferta de la lista, comprobar que la lista se actualiza y
    // que la oferta desaparece.
    @Test
    @Order(19)
    public void PR019() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        //Voy a la pagina
        driver.navigate().to("http://localhost:8090/offer/list");

        // Cargamos la lista de ofertas
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, "text", "eliminar",
                PO_View.getTimeout());
        int numOffers = elements.size();

        // Eliminamos la última oferta
        elements.get(numOffers - 1).click();

        //Comprobamos que se ha eliminado
        elements = SeleniumUtils.waitLoadElementsBy(driver, "text", "eliminar", PO_View.getTimeout());

        Assertions.assertEquals(numOffers - 1, elements.size());
    }

    // Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que
    // corresponde con el listado de las ofertas existentes en el sistema
    @Test
    @Order(20)
    public void PR020() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        //Escribo el texto a buscar
        PO_SearchView.searchForSale(driver, "", "//html/body/div/form/button");
        //Comprobamos la lista
        List<WebElement> offerList = driver.findElements(By.xpath("//tbody/tr"));
        int i = 1;
        int total = 0;
        String url = "http://localhost:8090/offer/listSearch?page=";
        while (!offerList.isEmpty()) {
            total += offerList.size();
            driver.navigate().to(url + i);
            offerList = driver.findElements(By.xpath("//tbody/tr"));
            i++;
        }
        Assertions.assertEquals(offersService.getOffers().size(), total);
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se
    // muestra la página que corresponde, con la lista de ofertas vacía
    @Test
    @Order(21)
    public void PR021() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        //Escribo el texto a buscar
        PO_SearchView.searchForSale(driver, "Texto a buscar", "//html/body/div/form/button");
        //Comprobamos la lista
        List<WebElement> offerList = driver.findElements(By.xpath("//tbody/tr"));
        Assertions.assertEquals(0, offerList.size());
        // Hacemos logout
        PO_View.logout(driver);
    }

    //Sobre una búsqueda determinada (a elección del desarrollador), comprar una oferta que deja un saldo positivo
    // en el contador del comprador. Comprobar que el contador se actualiza correctamente en la vista del comprador.
    @Test
    @Order(22)
    public void PR022() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        //Escribo el texto a buscar
        PO_SearchView.searchForSale(driver, "SNK", "//html/body/div/form/button");
        // Comprobamos que antes tengo 100€
        String checkText = "100 €";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Compramos el producto
        By enlace = By.xpath("//a[contains(@href, 'offer/buy')]");
        driver.findElement(enlace).click();
        // Comprobamos que ahora tengo 93€
        checkText = "93 €";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Sobre una búsqueda determinada (a elección del desarrollador), comprar una oferta que deja un saldo 0 en el
    // contador del comprador. Comprobar que el contador se actualiza correctamente en la vista del comprador.
    @Test
    @Order(23)
    public void PR023() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user04@email.com", "user04");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        //Escribo el texto a buscar
        PO_SearchView.searchForSale(driver, "bici", "//html/body/div/form/button");
        // Comprobamos que antes tengo 100€
        String checkText = "100 €";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Compramos el producto
        By enlace = By.xpath("//a[contains(@href, 'offer/buy')]");
        driver.findElement(enlace).click();
        // Comprobamos que ahora tengo 0€
        checkText = "0 €";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Sobre una búsqueda determinada (a elección del desarrollador), intentar comprar una oferta que esté por encima
    // de saldo disponible del comprador. Y comprobar que se muestra el mensaje de saldo no suficiente.
    @Test
    @Order(24)
    public void PR024() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user07@email.com", "user07");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        //Escribo el texto a buscar
        PO_SearchView.searchForSale(driver, "xiaomi", "//html/body/div/form/button");
        // Comprobamos que antes tengo 100€
        String checkText = "100 €";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Intentamos comprar el producto
        By enlace = By.xpath("//a[contains(@href, 'offer/buy')]");
        driver.findElement(enlace).click();
        // Comprobamos que se muestra el mensaje de error
        checkText = "No tiene suficiente dinero para comprar la oferta";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Ir a la opción de ofertas compradas del usuario y mostrar la lista. Comprobar que aparecen
    // las ofertas que deben aparecer.
    @Test
    @Order(25)
    public void PR025() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");
        // Vamos a ver mis compras
        driver.navigate().to("http://localhost:8090/offer/listPurchased");
        // Compruebo que el usuario tiene dos ofertas compradas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//table/tbody/tr");
        Assertions.assertEquals(elements.size(), 2);
        // Hacemos logout
        PO_View.logout(driver);
    }

    @Test
    @Order(26)
    public void PR026() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        By enlace = By.xpath("//a[contains(@href, 'conversations/add')]");
        driver.findElement(enlace).click();
        //Escribo el mensaje
        PO_PrivateView.sendMessage(driver, "Hola");
        //Comprobamos la lista
        List<WebElement> converstionsList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertEquals(1, converstionsList.size());
        // Hacemos logout
        PO_View.logout(driver);
    }

    @Test
    @Order(27)
    public void PR027() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user07@email.com", "user07");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        By enlace = By.xpath("//a[contains(@href, 'conversations/add')]");
        driver.findElement(enlace).click();

        // Hacemos logout
        PO_View.logout(driver);

        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/conversations/list");
        enlace = By.xpath("//a[contains(@href, 'conversations/get/add')]");
        driver.findElement(enlace).click();
        //Escribo el texto a buscar
        PO_PrivateView.sendMessage(driver, "Hola");
        //Comprobamos la lista
        List<WebElement> converstionsList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertEquals(1, converstionsList.size());
        // Hacemos logout
        PO_View.logout(driver);
    }

    @Test
    @Order(28)
    public void PR028() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        By enlace = By.xpath("//a[contains(@href, 'conversations/add')]");
        driver.findElement(enlace).click();
        //Escribo el texto a buscar
        PO_PrivateView.sendMessage(driver, "Hola");
        // Hacemos logout
        PO_View.logout(driver);

        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/conversations/list");
        //Comprobamos la lista
        List<WebElement> converstionsList = driver.findElements(By.xpath("//table/tbody/tr"));
        //I have the conversation from the test 26 and 27
        Assertions.assertEquals(1, converstionsList.size());
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Visualizar al menos cuatro páginas en español/inglés/español (comprobando que algunas de
    //las etiquetas cambian al idioma correspondiente). Ejemplo, Página principal/Opciones Principales de
    //Usuario/Listado de Usuarios.
    @Test
    @Order(29)
    public void PR029() {
        // pagina principal
        driver.navigate().to("http://localhost:8090/");
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
                PO_Properties.getENGLISH());

        // opciones principales de usuario
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");
        PO_NavView.changeLanguage(driver, "btnEnglish");
        PO_View.checkElementBy(driver, "text", "My Offers");
        //PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getENGLISH());
        PO_NavView.changeLanguage(driver, "btnSpanish");
        //PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
        PO_View.checkElementBy(driver, "text", "Mis Ofertas");
        List<WebElement> elements = driver.findElements(By.xpath("//tbody/tr"));
        Assertions.assertEquals(elements.size(), offersService.getOffersForUserEmail("user02@email.com").size());

        // Listado de Usuarios de Admin
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");
        driver.navigate().to("http://localhost:8090/user/list");
        PO_NavView.changeLanguage(driver, "btnEnglish");
        PO_View.checkElementBy(driver, "text", "List of users");
        PO_View.checkElementBy(driver, "text", "The users that are currently in the system are the following:");
        PO_NavView.changeLanguage(driver, "btnSpanish");
        PO_View.checkElementBy(driver, "text", "Lista de usuarios");
        PO_View.checkElementBy(driver, "text", "Los usuarios registrados en la web son los siguientes:");
        elements = driver.findElements(By.xpath("//tbody/tr"));
        Assertions.assertEquals(elements.size(), usersService.getUsers().size());

        // Vista de añadir de Oferta
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");
        driver.navigate().to("http://localhost:8090/offer/add");
        PO_NavView.changeLanguage(driver, "btnEnglish");
        PO_View.checkElementBy(driver, "text", "New Offer");
        PO_View.checkElementBy(driver, "text", "Detail");
        PO_NavView.changeLanguage(driver, "btnSpanish");
        PO_View.checkElementBy(driver, "text", "Nueva oferta");
        PO_View.checkElementBy(driver, "text", "Descripcion");
    }

    // Intentar acceder sin estar autenticado a la opción de listado de usuarios. Se deberá volver al
    //formulario de login.
    @Test
    @Order(30)
    public void PR030() {
        //Intentamos acceder al listado de usuarios de administrador
        driver.navigate().to("http://localhost:8090/user/list");
        // Comprobamos que nos redirige al formulario de login
        Assertions.assertTrue(PO_View.checkElementBy(driver, "text", "Identifícate").size() >= 1);
    }

    // Intentar acceder sin estar autenticado a la opción de listado de conversaciones. Se deberá
    //volver al formulario de login
    @Test
    @Order(31)
    public void PR031() {
        //Intentamos acceder al listado de coversaciones
        driver.navigate().to("http://localhost:8090/conversations/list");
        // Comprobamos que nos redirige al formulario de login
        Assertions.assertTrue(PO_View.checkElementBy(driver, "text", "Identifícate").size() >= 1);
    }

    // Estando autenticado como usuario estándar intentar acceder a una opción disponible solo
    //para usuarios administradores (Añadir menú de auditoria (visualizar logs)). Se deberá indicar un mensaje
    //de acción prohibida.
    @Test
    @Order(32)
    public void PR032() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");
        //Intentamos acceder al listado de usuarios
        driver.navigate().to("http://localhost:8090/user/list");
        // Comprobamos que nos redirige al formulario de login
        Assertions.assertTrue(PO_View.checkElementBy(driver, "text", "Error interno").size() >= 1);
    }

    // Estando autenticado como usuario administrador visualizar todos los logs generados en una
    // serie de interacciones. Esta prueba deberá generar al menos dos interacciones de cada tipo y comprobar
    // que el listado incluye los logs correspondientes.
    @Test
    @Order(33)
    public void PR033() {
        // ALTAS
        // Regitramos a un primer usuario
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_SignUpView.fillForm(driver, "miriam@email.com", "Miriam", "Gonzalez", "654321", "654321");
        // Comprobamos que nos redirige al home
        String checkText = "Mis Ofertas";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Hacemos logout
        PO_View.logout(driver);

        // Regitramos a un segundo usuario
        // Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_SignUpView.fillForm(driver, "luis@email.com", "Luis", "Lomba", "654321", "654321");
        // Comprobamos que nos redirige al home
        checkText = "Mis Ofertas";
        result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        // Hacemos logout
        PO_View.logout(driver);

        // LOGINS FALLIDOS
        // Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "luis@email.com", "123");
        PO_LoginView.fillLoginForm(driver, "luis@email.com", "456");


        // LOGINS CORRECTOS
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "luis@email.com", "654321");
        // Hacemos logout
        PO_View.logout(driver);

        // Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        // Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

        // Vamos a la pagina de logs
        driver.navigate().to("http://localhost:8090/log/list");

        // Comprobamos los logs de PET
        driver.navigate().to("http://localhost:8090/log/list?type=PET");

        // Comprobamos que hay al menos 2 logs
        List<WebElement> logList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertTrue(logList.size() >= 2);

        // Comprobamos los logs de ALTA
        driver.navigate().to("http://localhost:8090/log/list?type=ALTA");
        logList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertTrue(logList.size() >= 2);

        // Comprobamos los logs de LOGIN-EX
        driver.navigate().to("http://localhost:8090/log/list?type=LOGIN-EX");
        logList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertTrue(logList.size() >= 2);

        // Comprobamos los logs de LOGIN-ERR
        driver.navigate().to("http://localhost:8090/log/list?type=LOGIN-ERR");
        logList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertTrue(logList.size() >= 2);

        // Compobamos los logs de LOGOUT
        driver.navigate().to("http://localhost:8090/log/list?type=LOGOUT");
        logList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertTrue(logList.size() >= 2);

    }

    // Estando autenticado como usuario administrador, ir a visualización de logs, pulsar el
    // botón/enlace borrar logs y comprobar que se eliminan los logs de la base de datos.
    @Test
    @Order(34)
    public void PR034() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "admin@email.com", "admin");

        //Vamos a la pagina de logs
        driver.navigate().to("http://localhost:8090/log/list");

        //Borramos los logs
        By boton = By.id("btEliminar");
        driver.findElement(boton).click();

        //Comprobamos que no hay logs
        List<WebElement> logList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertEquals(logList.size(), 0);

    }

    @Test
    @Order(35)
    public void PR035() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        By enlace = By.xpath("//a[contains(@href, 'conversations/add')]");
        driver.findElement(enlace).click();
        //Escribo el texto a buscar
        PO_PrivateView.sendMessage(driver, "Hola");
        // Hacemos logout
        PO_View.logout(driver);

        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/conversations/list");
        // Damos al botón borrar
        List<WebElement> botonBorrar = PO_View.checkElementBy(driver, "class", "deleteConversation");
        botonBorrar.get(0).click();
        //Comprobamos la lista
        List<WebElement> converstionsList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertEquals(0, converstionsList.size());
        // Hacemos logout
        PO_View.logout(driver);
    }

    @Test
    @Order(36)
    public void PR036() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user02@email.com", "user02");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/offer/listSearch");
        By enlace = By.xpath("//a[contains(@href, 'conversations/add')]");
        driver.findElement(enlace).click();
        //Escribo el texto a buscar
        PO_PrivateView.sendMessage(driver, "Hola");
        // Hacemos logout
        PO_View.logout(driver);

        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");
        //Voy a la pagina de buscar
        driver.navigate().to("http://localhost:8090/conversations/list");

        List<WebElement> botonBorrar = PO_View.checkElementBy(driver, "class", "deleteConversation");
        botonBorrar.get(botonBorrar.size() - 1).click();

        //Comprobamos la lista
        List<WebElement> converstionsList = driver.findElements(By.xpath("//table/tbody/tr"));
        Assertions.assertEquals(0, converstionsList.size());
        // Hacemos logout
        PO_View.logout(driver);
    }

    // Al crear una oferta, marcar dicha oferta como destacada y a continuación comprobar: i) que
    // aparece en el listado de ofertas destacadas para los usuarios y que el saldo del usuario se actualiza
    // adecuadamente en la vista del ofertante (-20).
    @Test
    @Order(37)
    public void PR037() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        User user = usersService.getUserByEmail("user01@email.com");
        Long wallet = user.getWallet();

        //Vamos a la pagina de crear oferta
        driver.navigate().to("http://localhost:8090/offer/add");
        //Rellenamos el formulario
        PO_PrivateView.fillHighlightedOffer(driver, "Oferta destacada", "Destacada", 10);

        //Comprobamos que la oferta esta destacada
        List<WebElement> destacada = driver.findElements(By.xpath("//table/tbody/tr/td[contains(text(), 'Oferta destacada')]"));

        Assertions.assertEquals(1, destacada.size());

        //Comprobamos que el saldo se ha actualizado
        List<WebElement> result = PO_View.checkElementBy(driver, "text", wallet - 20 + "");

        Assertions.assertEquals(1, result.size());
    }

    // Sobre el listado de ofertas de un usuario con 20 euros (o más) de saldo, pinchar en el enlace
    // Destacada y a continuación comprobar: que aparece en el listado de ofertas destacadas para los usuarios
    // y que el saldo del usuario se actualiza adecuadamente en la vista del ofertante (-20).
    @Test
    @Order(38)
    public void PR038() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        User user = usersService.getUserByEmail("user01@email.com");
        Long wallet = user.getWallet();

        List<WebElement> aDestacar = driver.findElements(By.xpath("//a[contains(@href, 'offer/highlight/')]"));

        aDestacar.get(0).click();

        List<WebElement> sinDestacar = driver.findElements(By.xpath("//a[contains(@href, 'offer/highlight/')]"));

        Assertions.assertEquals(aDestacar.size() - 1, sinDestacar.size());

        //Comprobamos que el saldo se ha actualizado
        List<WebElement> result = PO_View.checkElementBy(driver, "text", wallet - 20 + "");

        Assertions.assertEquals(1, result.size());
    }

    // Sobre el listado de ofertas de un usuario con menos de 20 euros de saldo, pinchar en el enlace
    // Destacada y a continuación comprobar que se muestra el mensaje de saldo no suficiente.
    @Test
    @Order(39)
    public void PR039() {
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, "user01@email.com", "user01");

        User user = usersService.getUserByEmail("user01@email.com");
        Long wallet = user.getWallet();
        usersService.updateWallet(user, -wallet);

        // Obtenemos la lista de ofertas e intentamos destacar una
        List<WebElement> aDestacar = driver.findElements(By.xpath("//a[contains(@href, 'offer/highlight/')]"));
        aDestacar.get(0).click();

        // Comprobamos que no se ha destacado
        List<WebElement> sinDestacar = driver.findElements(By.xpath("//a[contains(@href, 'offer/highlight/')]"));
        Assertions.assertEquals(aDestacar.size(), sinDestacar.size());

        //Comprobamos que se muestra el mensaje de saldo insuficiente
        List<WebElement> result = PO_View.checkElementBy(driver, "text", "No tiene suficiente dinero para destacar la oferta");
        Assertions.assertEquals(1, result.size());

    }

}
