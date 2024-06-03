package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository = outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (partRepository.count() == 0 && productRepository.count() == 0) {

            //Creating Hockey Gear Parts
            Part hockeyStick = new InhousePart();
            Part hockeyPuck = new InhousePart();
            Part skates = new InhousePart();
            Part gloves = new InhousePart();
            Part helmet = new InhousePart();

            //Setting Names
            hockeyStick.setName("Hockey Stick");
            hockeyPuck.setName("Hockey Puck");
            skates.setName("Skates");
            gloves.setName("Gloves");
            helmet.setName("Helmet");

            //Setting Prices
            hockeyStick.setPrice(200);
            hockeyPuck.setPrice(5);
            skates.setPrice(150);
            gloves.setPrice(50);
            helmet.setPrice(80);

            //Setting Inventory's
            hockeyStick.setInv(30);
            hockeyPuck.setInv(100);
            skates.setInv(20);
            gloves.setInv(15);
            helmet.setInv(20);

            //Setting ID's
            hockeyStick.setId(1);
            hockeyPuck.setId(2);
            skates.setId(3);
            gloves.setId(4);
            helmet.setId(5);

            //Setting Max and Min Inventory
            hockeyStick.setMinInv(5);
            hockeyStick.setMaxInv(50);
            hockeyPuck.setMinInv(5);
            hockeyPuck.setMaxInv(200);
            skates.setMinInv(5);
            skates.setMaxInv(25);
            gloves.setMinInv(5);
            gloves.setMaxInv(20);
            helmet.setMinInv(5);
            helmet.setMaxInv(30);

            //Saving Items in Repository
            partRepository.save(hockeyStick);
            partRepository.save(hockeyPuck);
            partRepository.save(skates);
            partRepository.save(gloves);
            partRepository.save(helmet);
        }

        if (productRepository.count() == 0 && outsourcedPartRepository.count() == 0) {

            //Creating objects from product class
            Product youthStarterSet = new Product("Youth Hockey Set", 500, 30);
            Product starterHockeySet = new Product("Starter Hockey Set", 700, 10);
            Product advancedHockeySet = new Product("Advanced Hockey Set", 2500, 8);
            Product proHockeySet = new Product("Pro Hockey Set", 5000, 5);
            Product goalieSet = new Product("Goalie Set", 3000, 10);

            //Saving Products in Repository
            productRepository.save(youthStarterSet);
            productRepository.save(starterHockeySet);
            productRepository.save(advancedHockeySet);
            productRepository.save(proHockeySet);
            productRepository.save(goalieSet);
        }
    }
}
