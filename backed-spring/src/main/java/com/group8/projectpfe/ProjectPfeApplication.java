package com.group8.projectpfe;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.SportifService;
import com.group8.projectpfe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ProjectPfeApplication implements CommandLineRunner{
	@Autowired
	private SportRepository sportRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectPfeApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		List<Sport> sports = List.of(
				new Sport("Football", "Description of Football", "https://wallpapercave.com/wp/wp2167295.jpg"),
				new Sport("Basketball", "Description of Basketball", "https://th.bing.com/th/id/R.7c738c92a670fdb34fa5481d3194bc5c?rik=82uTuwJHEViUew&riu=http%3a%2f%2fwww.gannett-cdn.com%2f-mm-%2f59ed8fafe594c4d7be1f8055531272d0b6bec057%2fc%3d0-108-1557-1279%2flocal%2f-%2fmedia%2f2015%2f10%2f28%2fMountainHome%2fMountainHome%2f635816507979907361-Basketball.jpg&ehk=SuvUxk25207HOO691chNG2qYk81ac1kArRJdd20qowY%3d&risl=&pid=ImgRaw&r=0"),
				new Sport("Volleyball", "Description of Volleyball", "https://versesfrommama.com/wp-content/uploads/2015/10/VFM-Volleyball-1024x683.jpg"),
				new Sport("Tennis", "Description of Tennis", "https://d2fy2et424xkoh.cloudfront.net/blog/wp-content/uploads/2021/06/Tennis-02.jpg"),
				new Sport("Golf", "Description of Golf", "https://th.bing.com/th/id/OIP.whil78cMzy590yRxAuLyCwHaE8?rs=1&pid=ImgDetMain"),
				new Sport("Baseball", "Description of Baseball", "https://th.bing.com/th/id/OIP.M1u8CPaq9dhmNS1NCD15ygHaHL?rs=1&pid=ImgDetMain"),
				new Sport("Soccer", "Description of Soccer", "https://th.bing.com/th/id/OIP.mHva60UBbP6TKMtyjFevrgHaEK?rs=1&pid=ImgDetMain"),
				new Sport("Hockey", "Description of Hockey", "https://th.bing.com/th/id/R.1c44474930129c512bb115dd3f880b17?rik=d0PQlvybqhWprg&pid=ImgRaw&r=0"),
				new Sport("Swimming", "Description of Swimming", "https://cdn.britannica.com/83/126383-050-38B8BE25/Michael-Phelps-American-Milorad-Cavic-final-Serbia-2008.jpg")

		);

		for (Sport sport : sports) {
			if (!sportRepository.existsByName(sport.getName())) {
				sportRepository.save(sport);
			}
		}
	}
}
