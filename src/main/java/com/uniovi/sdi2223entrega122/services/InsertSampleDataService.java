package com.uniovi.sdi2223entrega122.services;

import com.uniovi.sdi2223entrega122.entities.Offer;
import com.uniovi.sdi2223entrega122.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class InsertSampleDataService {
    @Autowired
    private UsersService user0sService;
    @Autowired
    private RolesService rolesService;

    @Autowired
    private OffersService offersService;

    @PostConstruct
    public void init() {
        User admin = new User("admin@email.com", "admin", "admin");
        admin.setPassword("admin");
        admin.setRole(rolesService.getRoles()[1]);

        User user00 = new User("user00@email.com", "Ruben", "Sierra");
        user00.setPassword("user00");
        user00.setRole(rolesService.getRoles()[0]);

        User user01 = new User("user01@email.com", "Pedro", "Díaz");
        user01.setPassword("user01");
        user01.setRole(rolesService.getRoles()[0]);

        User user02 = new User("user02@email.com", "Lucas", "Núñez");
        user02.setPassword("user02");
        user02.setRole(rolesService.getRoles()[0]);

        User user03 = new User("user03@email.com", "María", "Rodríguez");
        user03.setPassword("user03");
        user03.setRole(rolesService.getRoles()[0]);

        User user04 = new User("user04@email.com", "Marta", "Almonte");
        user04.setPassword("user04");
        user04.setRole(rolesService.getRoles()[0]);

        User user05 = new User("user05@email.com", "Pelayo", "Valdes");
        user05.setPassword("user05");
        user05.setRole(rolesService.getRoles()[0]);

        User user06 = new User("user06@email.com", "Edward", "Núñez");
        user06.setPassword("user06");
        user06.setRole(rolesService.getRoles()[0]);

        User user07 = new User("user07@email.com", "Anabel", "Alonso");
        user07.setPassword("user07");
        user07.setRole(rolesService.getRoles()[0]);

        User user08 = new User("user08@email.com", "Marcelino", "Gonzalez");
        user08.setPassword("user08");
        user08.setRole(rolesService.getRoles()[0]);

        User user09 = new User("user09@email.com", "Luis", "Lomba");
        user09.setPassword("user09");
        user09.setRole(rolesService.getRoles()[0]);

        User user010 = new User("user010@email.com", "Mateo", "Rico");
        user010.setPassword("user010");
        user010.setRole(rolesService.getRoles()[0]);

        User user011 = new User("user011@email.com", "Javier", "Gonzalez");
        user011.setPassword("user011");
        user011.setRole(rolesService.getRoles()[0]);

        User user012 = new User("user012@email.com", "Esther", "Alonso");
        user012.setPassword("user012");
        user012.setRole(rolesService.getRoles()[0]);

        User user013 = new User("user013@email.com", "Daniel", "Martinez");
        user013.setPassword("user013");
        user013.setRole(rolesService.getRoles()[0]);

        User user014 = new User("user014@email.com", "Lucas", "Ruiz");
        user014.setPassword("user014");
        user014.setRole(rolesService.getRoles()[0]);

        Set<Offer> user01Offers = new HashSet<>() {
            {
                add(new Offer("Pokemon Escarlata", "Videojuego", 50L, user01));
                add(new Offer("Arena Blanca", "Libro", 20L, user01));
                add(new Offer("Xiaomi 5T", "Movil", 550L, user01));
                add(new Offer("Camiseta blanca", "Ropa", 15L, user01));
                add(new Offer("Acer", "Ordenador", 100L, user01));
                add(new Offer("Renault Clio", "Coche", 4000L, user01));
                add(new Offer("Soporte auriculares", "Accesorio", 20L, user01));
                add(new Offer("Camiseta el nano", "Ropa", 10L, user01));
                add(new Offer("Boligrafo bic", "Educacion", 2L, user01));
                add(new Offer("Trenza del mar esmeralda", "Libro", 15L, user01));
            }
        };
        Set<Offer> user02Offers = new HashSet<>() {
            {
                add(new Offer("SNK", "Manga", 7L, user02));
                add(new Offer("Zapato tacon", "Zapatos", 4L, user02));
                add(new Offer("Tejano", "Pantalon", 7L, user02));
                add(new Offer("Patinete", "Ocio", 70L, user02));
                add(new Offer("Bicicleta", "Transporte", 100L, user02));
                add(new Offer("Manta electrica", "Ropa", 20L, user02));
                add(new Offer("Zelda BOTW", "Videojuego", 40L, user02));
                add(new Offer("Apple watch", "Tecnologia", 60L, user02));
                add(new Offer("Escritorio IKEA", "Mueble", 10L, user02));
                add(new Offer("Mancuernas", "Deporte", 5L, user02));
            }
        };
        Set<Offer> user03Offers = new HashSet<>() {
            {
                add(new Offer("Lamina NY", "Arte", 5L, user03));
                add(new Offer("Asterix y Obelix", "Libro", 5L, user03));
                add(new Offer("Barrica", "Agricultura", 10L, user03));
                add(new Offer("Hoz", "Herramienta", 3L, user03));
                add(new Offer("Hollow Night", "Videojuego", 30L, user03));
                add(new Offer("Almendra", "Libro", 5L, user03));
                add(new Offer("Mando PS4", "Tecnologia", 7L, user03));
                add(new Offer("Reloj", "Accesorio", 5L, user03));
                add(new Offer("Mesa auxiliar", "Mueble", 35L, user03));
                add(new Offer("Bateria", "Musica", 55L, user03));
            }
        };
        Set<Offer> user04Offers = new HashSet<>() {
            {
                add(new Offer("Marina", "Libro", 6L, user04));
                add(new Offer("Samsung Galaxy Buds", "Electronica", 40L, user04));
                add(new Offer("Xiaomi LED", "Electronica", 30L, user04));
                add(new Offer("Estanteria", "Mueble", 42L, user04));
                add(new Offer("Disfraz egipcio", "Ropa", 4L, user04));
                add(new Offer("Caldera", "Mueble", 24L, user04));
                add(new Offer("Desbrozadora", "Agricultura", 100L, user04));
                add(new Offer("Haikyuu", "Manga", 5L, user04));
                add(new Offer("MP4", "Electronica", 30L, user04));
                add(new Offer("Martillo", "Herramienta", 4L, user04));
            }
        };
        Set<Offer> user05Offers = new HashSet<>() {
            {
                add(new Offer("Nintendo Switch", "Consola", 150L, user05));
                add(new Offer("Canon 3500", "Camara", 120L, user05));
                add(new Offer("Nescafe Dolce Gusto", "Cafetera", 45L, user05));
                add(new Offer("Razer Barracuda", "Auriculares", 60L, user05));
                add(new Offer("iPad", "Tablet", 100L, user05));
                add(new Offer("Air Frier", "Electrodomestico", 60L, user05));
                add(new Offer("Microondas", "Electrodomestico", 20L, user05));
                add(new Offer("Xiaomi TV", "Television", 70L, user05));
                add(new Offer("Sudadera kaotiko", "Ropa", 60L, user05));
                add(new Offer("Termo", "Electrodomestico", 30L, user05));
            }
        };
        Set<Offer> user06Offers = new HashSet<>() {
            {
                add(new Offer("Humidificador", "Decoracion", 10L, user06));
                add(new Offer("Cesta mimbre", "Accesorio", 5L, user06));
                add(new Offer("Bonsai lego", "Decoracion", 30L, user06));
                add(new Offer("Marco fotos", "Accesorio", 5L, user06));
                add(new Offer("Pluma estilografica", "Escritura", 40L, user06));
                add(new Offer("One Plus Nord 2", "Movil", 170L, user06));
                add(new Offer("Animal Crossing", "Videojuego", 40L, user06));
                add(new Offer("Taza dragon ball", "Vajilla", 170L, user06));
                add(new Offer("Air Pods", "Electronica", 40L, user06));
                add(new Offer("Telescopio", "Ocio", 270L, user06));
            }
        };

        Set<Offer> user07Offers = new HashSet<>() {
            {
                add(new Offer("Olympus", "Camara", 80L, user07));
                add(new Offer("Tripode", "Accesorio", 15L, user07));
                add(new Offer("Raqueta padel", "Deporte", 25L, user07));
                add(new Offer("Skate penny", "Ocio", 50L, user07));
                add(new Offer("Playeros adidas", "Ropa", 40L, user07));
                add(new Offer("Rotuladores alcohol", "Dibujo", 170L, user07));
                add(new Offer("Mochila Real Madrid", "Accesorio", 170L, user07));
                add(new Offer("Mesa hockey", "Ocio", 200L, user07));
                add(new Offer("Forrest Gump", "Pelicula", 30L, user07));
                add(new Offer("Elantris", "Libro", 10L, user07));
            }
        };

        Set<Offer> user08Offers = new HashSet<>() {
            {
                add(new Offer("La hija de la noche", "Libro", 8L, user08));
                add(new Offer("Cuna", "Bebe", 70L, user08));
                add(new Offer("Playmobil", "Juguete", 40L, user08));
                add(new Offer("Peluche elmo", "Juguete", 5L, user08));
                add(new Offer("Quad", "Transporte", 300L, user08));
                add(new Offer("Lego star wars", "Juguete", 52L, user08));
                add(new Offer("Flexo", "Luz", 20L, user08));
                add(new Offer("Tomtom", "Navegador GPS", 40L, user08));
                add(new Offer("Webcam HP", "Tecnologia", 170L, user08));
                add(new Offer("Nikon", "Camara", 300L, user08));
            }
        };

        Set<Offer> user09Offers = new HashSet<>() {
            {
                add(new Offer("Cuentakilometros", "Accesorio bicicleta", 13L, user09));
                add(new Offer("Friends", "Serie", 50L, user09));
                add(new Offer("Lamina studio ghibli", "Decoracion", 20L, user09));
                add(new Offer("Posavasos", "Accesorio", 2L, user09));
                add(new Offer("Limpiador cerave", "Belleza", 9L, user09));
                add(new Offer("Crema manos", "Cuidado de piel", 10L, user09));
                add(new Offer("Hollow knight", "Videojuego", 40L, user09));
                add(new Offer("Secador pelo", "Accesorio", 15L, user09));
                add(new Offer("Apple pencil", "tecnologia", 80L, user09));
                add(new Offer("Tijeras", "Escuela", 2L, user09));
            }
        };

        Set<Offer> user010Offers = new HashSet<>() {
            {
                add(new Offer("Taza personalizada", "Vajilla", 5L, user010));
                add(new Offer("Colgante llave", "Accesorio", 12L, user010));
                add(new Offer("Sofa", "Mueble", 200L, user010));
                add(new Offer("Coleccion cromos liga", "Coleccion", 50L, user010));
                add(new Offer("Lavadora", "Electrodomestico", 99L, user010));
                add(new Offer("Invictus", "Colonia", 40L, user010));
                add(new Offer("Mampara", "Mueble", 200L, user010));
                add(new Offer("Balon de futbol", "Deporte", 10L, user010));
                add(new Offer("Botas de futbol", "Deporte", 15L, user010));
                add(new Offer("Ajedrez", "Ocio", 8L, user010));
            }
        };

        Set<Offer> user011Offers = new HashSet<>() {
            {
                add(new Offer("F1 Manager 2022", "Videojuego", 40L, user011));
                add(new Offer("Marvel Spiderman Remastered", "Videojuego", 50L, user011));
                add(new Offer("Hogwarts Legacy", "Videojuego", 70L, user011));
                add(new Offer("Elden Ring", "Videojuego", 55L, user011));
                add(new Offer("GTA V", "Videojuego", 46L, user011));
                add(new Offer("Bioshock 2", "Videojuego", 60L, user011));
                add(new Offer("Mafia 2", "Videojuego", 70L, user011));
                add(new Offer("Hellblade", "Videojuego", 40L, user011));
                add(new Offer("Resident Evil Village", "Videojuego", 45L, user011));
                add(new Offer("Sekiro", "Videojuego", 70L, user011));
            }
        };

        Set<Offer> user012Offers = new HashSet<>() {
            {
                add(new Offer("Las lagrimas de shiva", "Libro", 10L, user012));
                add(new Offer("El si de las niñas", "Libro", 5L, user012));
                add(new Offer("La bibliotecaria de Auschwitz", "Libro", 7L, user012));
                add(new Offer("El viejo y el mar", "Libro", 8L, user012));
                add(new Offer("El ojo del tiempo", "Libro", 6L, user012));
                add(new Offer("El secreto del tiempo", "Libro", 9L, user012));
                add(new Offer("Luces de bohemia", "Libro", 11L, user012));
                add(new Offer("El chico de la ultima fila", "Libro", 17L, user012));
                add(new Offer("La puerta de los tres cerrojos", "Libro", 13L, user012));
                add(new Offer("Novia gitana", "Libro", 12L, user012));
            }
        };

        Set<Offer> user013Offers = new HashSet<>() {
            {
                add(new Offer("Pinocho", "Pelicula", 10L, user013));
                add(new Offer("Oceans eleven", "Pelicula", 5L, user013));
                add(new Offer("Top Gun", "Pelicula", 30L, user013));
                add(new Offer("Hora punta", "Pelicula", 5L, user013));
                add(new Offer("Rocky IV", "Pelicula", 40L, user013));
                add(new Offer("Fast and furious", "Pelicula", 170L, user013));
                add(new Offer("Torrente", "Pelicula", 170L, user013));
                add(new Offer("El juego de ender", "Pelicula", 170L, user013));
                add(new Offer("Joker", "Pelicula", 170L, user013));
                add(new Offer("REC", "Pelicula", 170L, user013));
            }
        };

        Set<Offer> user014Offers = new HashSet<>() {
            {
                add(new Offer("Aqui no hay quien viva", "Serie", 10L, user014));
                add(new Offer("El internado", "Serie", 15L, user014));
                add(new Offer("Fisica o quimica", "Serie", 10L, user014));
                add(new Offer("La que se avecina", "Serie", 15L, user014));
                add(new Offer("Los Serrano", "Serie", 20L, user014));
                add(new Offer("Aida", "Serie", 15L, user014));
                add(new Offer("La casa de papel", "Serie", 16L, user014));
                add(new Offer("El barco", "Serie", 10L, user014));
                add(new Offer("Vis a vis", "Serie", 5L, user014));
                add(new Offer("Los hombres de Paco", "Serie", 10L, user014));
            }
        };

        user01.setOffers(user01Offers);
        user02.setOffers(user02Offers);
        user03.setOffers(user03Offers);
        user04.setOffers(user04Offers);
        user05.setOffers(user05Offers);
        user06.setOffers(user06Offers);
        user07.setOffers(user07Offers);
        user08.setOffers(user08Offers);
        user09.setOffers(user09Offers);
        user010.setOffers(user010Offers);
        user011.setOffers(user011Offers);
        user012.setOffers(user012Offers);
        user013.setOffers(user013Offers);
        user014.setOffers(user014Offers);

        user0sService.addUser(user00);
        user0sService.addUser(user01);
        user0sService.addUser(user02);
        user0sService.addUser(user03);
        user0sService.addUser(user04);
        user0sService.addUser(user05);
        user0sService.addUser(user06);
        user0sService.addUser(user07);
        user0sService.addUser(admin);
        user0sService.addUser(user08);
        user0sService.addUser(user09);
        user0sService.addUser(user010);
        user0sService.addUser(user011);
        user0sService.addUser(user012);
        user0sService.addUser(user013);
        user0sService.addUser(user014);

        Offer boughtOffer1 = offersService.getOffersForUser(user04).get(1);
        Offer boughtOffer2 = offersService.getOffersForUser(user04).get(3);

        offersService.buyOffer(boughtOffer1.getId(), user02);
        offersService.buyOffer(boughtOffer2.getId(), user02);
    }
}
