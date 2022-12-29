package com.example.proyectoBancario3;

import com.example.proyectoBancario3.entities.Client;
import com.example.proyectoBancario3.entities.User;
import com.example.proyectoBancario3.repository.UserRepository;
import com.example.proyectoBancario3.services.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class ProyectoBancario3Application {

	public static void main(String[] args) {



//      ----------------------------------------------------------------
//		SpringApplication.run(ProyectoBancario3Application.class, args);
//		ApplicationContext context = SpringApplication.run(ProyectoBancario3Application.class, args);
//		ApplicationContext beanContext = new ClassPathXmlApplicationContext("beans.xml");
//
//		UserRepository repository = context.getBean(UserRepository.class);
//		MainService mainService = (MainService) beanContext.getBean("MainService");
//		mainService.setRepository(repository);
//		mainService.mainServ();
// ----------------------------------------------------------------


		MainService mainService = new MainService();
//		mainService.setRepository(repository);
		mainService.menu();

//		UserRepository repository = context.getBean(UserRepository.class);
////
//		System.out.println("Encontrar");
//		System.out.println("Total de usuarios es : " + repository.count());
//		System.out.println("------------------");
////
//		Client usuario1= new Client ("Juan","Carlos","123","juancarlos@gmail.com",new User(null, "Juan","Carlos","1554","juan@gmail.com"));
//		repository.save(usuario1);
//		Client usuario2= new Client ("Señor","Burns","123","burns@gmail.com",new User(null, "Señor","Burns","1554232","burns@gmail.com"));
//		repository.save(usuario2);

		//Buscar datos guardados
//		System.out.println(repository.findAll());
//		System.out.println("Total de usuarios es : " + repository.count());
	}

}
