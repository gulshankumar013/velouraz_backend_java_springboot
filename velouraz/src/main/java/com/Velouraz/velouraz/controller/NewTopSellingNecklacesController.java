import com.Velouraz.velouraz.dto.NewTopSellingNecklacesRequest;
import com.Velouraz.velouraz.entity.NewTopSellingNecklaces;
import com.Velouraz.velouraz.repository.NewTopSellingNecklacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/new-top-selling-necklaces")
@CrossOrigin(origins = "http://localhost:5173")
public class NewTopSellingNecklacesController {

    @Autowired
    private NewTopSellingNecklacesRepository repo;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody NewTopSellingNecklacesRequest r) {

        NewTopSellingNecklaces n = new NewTopSellingNecklaces();
        n.setTitle(r.getNecklace_sets_title());
        n.setPrice(r.getNecklace_sets_price());
        n.setDetails(r.getNecklace_sets_details());
        n.setImage1(r.getNecklace_sets_image());
        n.setImage2(r.getNecklace_sets_image2());
        n.setImage3(r.getNecklace_sets_image3());

        NewTopSellingNecklaces saved = repo.save(n);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Top selling necklace added successfully",
                        "data", Map.of(
                                "id", saved.getId(),
                                "title", saved.getTitle(),
                                "price", saved.getPrice(),
                                "details", saved.getDetails(),
                                "images", List.of(
                                        saved.getImage1(),
                                        saved.getImage2(),
                                        saved.getImage3()
                                ).stream().filter(i -> i != null).toList()
                        )
                )
        );
    }
}
