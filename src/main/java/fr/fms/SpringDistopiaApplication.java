package fr.fms;

import fr.fms.dao.*;
import fr.fms.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringDistopiaApplication implements CommandLineRunner {

	@Autowired
	CinemaRepository cinemaRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	FilmRepository filmRepository;

	@Autowired
	ShowingRepository showingRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringDistopiaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		generateData();
	}

	public void generateData(){
		City toulouse = new City(null, "Toulouse", null);
		City nice = new City(null, "nice", null);
		City brive = new City(null, "Brive-la-Gaillarde", null);
		City paris = new City(null, "Paris", null);
		City strasbourg = new City(null, "Strasbourg", null);
		City rennes = new City(null, "Rennes", null);

		cityRepository.save(toulouse);
		cityRepository.save(nice);
		cityRepository.save(brive);
		cityRepository.save(paris);
		cityRepository.save(strasbourg);
		cityRepository.save(rennes);

		Showing tenAm = new Showing(null, "10:45", null);
		Showing thirteenPm = new Showing(null, "13:45", null);
		Showing fifteenPm = new Showing(null, "15:05", null);
		Showing sixteenPm = new Showing(null, "16:45", null);
		Showing seventeenPm = new Showing(null, "17:00", null);
		Showing eightyPm = new Showing(null, "20:00", null);
		Showing ninetyPm = new Showing(null, "21:00", null);

		showingRepository.save(tenAm);
		showingRepository.save(thirteenPm);
		showingRepository.save(fifteenPm);
		showingRepository.save(sixteenPm);
		showingRepository.save(seventeenPm);
		showingRepository.save(eightyPm);
		showingRepository.save(ninetyPm);

		List<Showing> showingList = new ArrayList<>();
		showingList.add(tenAm);
		showingList.add(thirteenPm);
		showingList.add(fifteenPm);
		showingList.add(sixteenPm);
		showingList.add(seventeenPm);
		showingList.add(eightyPm);
		showingList.add(ninetyPm);

		Film fallGuy = new Film(null, "The Fall guy", "C'est l’histoire d’un cascadeur, et comme tous les cascadeurs, il se fait tirer dessus et tombe toujours de plus en plus haut…", "2h05", null, showingList);
		Film godzilla = new Film(null, "Godzilla x Kong : Le nouvel Empire", "Le tout-puissant Kong et le redoutable Godzilla unissent leurs forces contre une terrible menace.", "1h55", null, showingList);
		Film challenger = new Film(null, "Challengers", "Durant leurs études, Patrick et Art, tombent amoureux de Tashi. À la fois amis, amants et rivaux.", "2h11", null, showingList);
		Film civilWar = new Film(null, "Civil War", "Une course effrénée à travers une Amérique fracturée qui, dans un futur proche, est plus que jamais sur le fil du rasoir.", "1h49", null, showingList);
		Film dune = new Film(null, "Dune : part two", "Dans DUNE : DEUXIÈME PARTIE, Paul Atreides s’unit à Chani et aux Fremen pour mener la révolte contre ceux qui ont anéanti sa famille.", "2h46", null, showingList);

		filmRepository.save(fallGuy);
		filmRepository.save(godzilla);
		filmRepository.save(challenger);
		filmRepository.save(civilWar);
		filmRepository.save(dune);

		List<Film> filmList = new ArrayList<>();
		filmList.add(fallGuy);
		filmList.add(godzilla);
		filmList.add(challenger);
		filmList.add(civilWar);
		filmList.add(dune);

		cinemaRepository.save(new Cinema(null, "Gaumont", "32, rue Louis Legrand 75009 Paris", paris, filmList));
		cinemaRepository.save(new Cinema(null, "Pathé", "3, place du President-Wilson 31000 Toulouse", toulouse, filmList));
		cinemaRepository.save(new Cinema(null, "UGC", "Place Marcel Bouilloux-Lafont 31400 Toulouse", toulouse, filmList));
		cinemaRepository.save(new Cinema(null, "Le grand Rex", "1, bd Poissonniere 75002 Paris", paris, filmList));
		cinemaRepository.save(new Cinema(null, "mk2 bibliothèque", "128-162 avenue de France 75013 Paris", paris, filmList));
		cinemaRepository.save(new Cinema(null, "Cinéma Variétés", "5, bd Victor-Hugo 06000 Nice", nice, filmList));
		cinemaRepository.save(new Cinema(null, "Cinéma Rex", "3, bd du General-Koenig 19100 Brive-la-Gaillarde", brive, filmList));
		cinemaRepository.save(new Cinema(null, "Cinéma Vox", "17, rue des Francs-Bourgeois 67000 Strasbourg", strasbourg, filmList));
		cinemaRepository.save(new Cinema(null, "Cinéma Rialto", "4, rue de Rivoli 06000 Nice", nice, filmList));
		cinemaRepository.save(new Cinema(null, "Cinéma Arvor", "11 rue de Châtillon 35000 Rennes", rennes, filmList));
		cinemaRepository.save(new Cinema(null, "Megarama Nice", "21 Rue Jules et Aline Avigdor 06300 Nice", paris, filmList));

		Role user = roleRepository.save(new Role("USER", null));
		Role admin = roleRepository.save(new Role("ADMIN", null));
		createUserWithRoles("fred2024", "fmsAcad@2024$", true, admin, user);
		createUserWithRoles("Josette", "@Pelote2024!", true, user);
	}

	private void createUserWithRoles(String username, String password, boolean active, Role... roles) {
		List<Role> userRoles = Arrays.asList(roles);
		String encodedPassword = passwordEncoder.encode(password);
		userRepository.save(new User(username, encodedPassword, active, userRoles));
	}
}
