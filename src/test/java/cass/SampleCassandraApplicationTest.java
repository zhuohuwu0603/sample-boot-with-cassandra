package cass;

import cass.domain.Hotel;
import cass.domain.HotelByLetter;
import cass.repository.HotelByLetterRepository;
import cass.repository.HotelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleCassandraApplication.class)
public class SampleCassandraApplicationTest {

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private HotelByLetterRepository hotelByLetterRepository;

	@Test
	public void repositoryCrudOperations() {
		Hotel sample = sampleHotel();
		this.hotelRepository.save(sample);
		Hotel savedHotel = this.hotelRepository.findOne(sample.getId());
		assertThat(savedHotel.getName(), equalTo("Sample Hotel"));

		//this.hotelRepository.delete(savedHotel);

		HotelByLetter hotelByLetter = new HotelByLetter(sample);
		this.hotelByLetterRepository.save(hotelByLetter);
		HotelByLetter savedHotelByLetter = this.hotelByLetterRepository.findOne(hotelByLetter.getHotelByLetterKey());
		assertThat(savedHotelByLetter.getAddress(), equalTo("Sample Address2"));
		//this.hotelByLetterRepository.delete(savedHotelByLetter);

	}

	private Hotel sampleHotel() {
		Hotel hotel = new Hotel();
		//hotel.setId(UUID.randomUUID());
		hotel.setId(UUID.fromString("2f70cc0f-f25c-4903-bc3e-d783fe3e2920")); //a fixed hotel for test
		hotel.setName("Sample Hotel");
		hotel.setAddress("Sample Address2");
		hotel.setState("NYC");
		hotel.setZip("8764");
		return hotel;
	}

}
