package fr.fms;

import fr.fms.business.IBusinessImpl;
import fr.fms.dao.CinemaRepository;
import fr.fms.dao.CityRepository;
import fr.fms.entities.Cinema;
import fr.fms.entities.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class SpringDistopiaApplicationTests {

	@Autowired
	IBusinessImpl business;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CinemaRepository cinemaRepository;

	@Test
	void contextLoads() {
		int value1 = 1;
		int value2 = 2;
		assertNotEquals(value1, value2);
	}

	@Test
	void testSaveCity(){
		business.saveCity(new City(null, "Marseille", null));
		business.saveCity(new City(null, "Orléans", null));
		business.saveCity(new City(null, "Tulle", null));
	}

	@Test
	void test_add_cinema(){
		City anonymous = cityRepository.save(new City(null, "anonymous", null));
		cinemaRepository.save(new Cinema(null, "incognito", "rue de l'inconnu", anonymous, null));

		Cinema cinema = cinemaRepository.findByNameContains("incognito", Pageable.ofSize(1)).getContent().get(0);

		assertEquals("rue de l'inconnu", cinema.getAddress());
	}
}
