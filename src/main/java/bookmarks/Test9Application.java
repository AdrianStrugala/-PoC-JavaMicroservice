package bookmarks;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Optional;
import javax.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import bookmarks.aspects.ReFormat;

//tutaj pozmienialem do wara (byl normalny main bez extendu i configa)
@SpringBootApplication




public class Test9Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Test9Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Test9Application.class, args);
    }
}




@Component
class ProductCommandLineRunner implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
	
//	for (Basket b : this.basketRepository.findAll())
//		System.out.println(b.toString());
	}
	
	@Autowired ProductRepository productRepository;
}

/*Generuje Query*/
interface ProductRepository extends JpaRepository<Product, Long>{
	
	Collection<Product> findByProductName(String productName);
}

/*Klasa odpowiadajaca za tworzenie i wysylanie RESTA*/
@RestController
//@RequestMapping("/ags-1")
class ProductRestController {
	
	private static int queriesNumber = 0;
	
	public int getQueriesNumber() {
		return queriesNumber;
	}

	void incrementQueries(){
		
		queriesNumber ++;
	}

	//funkcja tworzca log
	void log(String nazwa){	

		try {
			PrintWriter logs = new PrintWriter(new BufferedWriter(new FileWriter("logs.txt",true)));
			logs.println(new Date() + " " + nazwa);
			logs.close();
			
			incrementQueries();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Nie znaleziono pliku");
			e.printStackTrace();
		}	

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/stats")
    public String stats() {
		log("Product list");
		return "Number of queries: " + getQueriesNumber();
		
    }
	

	@RequestMapping("/product")
	Collection <Product> products(){
		
		log("Product list");
		
		return this.productRepository.findAll();
		
		
		
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = "product/{productId}")
//	Optional<Product> readProduct(@PathVariable Long productId) {
//		
//		log("Product no. " + productId);
//		
//		return this.productRepository.findOne(productId);
//	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Product input) {
	Product result = this.productRepository.save(new Product(input.getproductName(), input.getprize(), input.gettype()));
	
	URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(result.getId()).toUri();
	
	return ResponseEntity.created(location).build();
	}
	@Autowired ProductRepository productRepository;
}
/* To jest klasa odpowiadajaca za tworzenie tabeli, wyszczegolnione sa kolumny */
@Entity
class Product{
	
	@Id @GeneratedValue
	private Long id;
	private String productName;
	private double prize;
	private String type;
	
	public Product(String productName, double prize, String type) {
		super();
		this.productName = productName;
		this.prize = prize;
		this.type = type;
	}
	public Product() {
		super();
	}
	public Long getId() {
		return id;
	}
	public double getprize() {
		return prize;
	}
	@ReFormat
	public String getproductName() {
		return productName;
	}
	
	public String gettype() {
		return type;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void settype(String type) {
		this.type = type;
	}
	
	public void setPrize(double prize) {
		this.prize = prize;
	}
	@Override
	@ReFormat
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", prize=" + prize + ", type=" + type + "]";
	}
}

