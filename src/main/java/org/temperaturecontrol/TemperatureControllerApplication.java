package org.temperaturecontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TemperatureControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureControllerApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ThermostatRepository thermostatRepository, ApartmentRepository apartmentRepository) {
		return (evt) -> {
		thermostatRepository.save(new Thermostat("Cold", 72));
		thermostatRepository.save(new Thermostat("Cold", 68));
		thermostatRepository.save(new Thermostat("Cold", 55));
		thermostatRepository.save(new Thermostat("Heat", 72));
		thermostatRepository.save(new Thermostat("Heat", 75));
		thermostatRepository.save(new Thermostat("Heat", 78));
		List<Thermostat> thermostatList1 = new ArrayList<Thermostat>();
		thermostatList1.add(new Thermostat("Cold", 72));
		thermostatList1.add(new Thermostat("Heat", 72));
		List<Thermostat> thermostatList2 = new ArrayList<Thermostat>();
		thermostatList2.add(new Thermostat("Cold", 68));
		List<Thermostat> thermostatList3 = new ArrayList<Thermostat>();
		thermostatList3.add(new Thermostat("Cold", 72));
		thermostatList3.add(new Thermostat("Heat", 75));
		List<Thermostat> thermostatList4 = new ArrayList<Thermostat>();
		thermostatList4.add(new Thermostat("Heat", 78));
		apartmentRepository.save(new Apartment(thermostatList1));
		apartmentRepository.save(new Apartment(thermostatList2));
		apartmentRepository.save(new Apartment(thermostatList3));
		apartmentRepository.save(new Apartment(thermostatList4));	
		};
	}
/*
	@Bean
	CommandLineRunner init(ApartmentRepository apartmentRepository) {
		List<Thermostat> thermostatList1 = new ArrayList<Thermostat>();
		thermostatList1.add(new Thermostat("Cold", 72));
		thermostatList1.add(new Thermostat("Heat", 72));
		List<Thermostat> thermostatList2 = new ArrayList<Thermostat>();
		thermostatList2.add(new Thermostat("Cold", 68));
		List<Thermostat> thermostatList3 = new ArrayList<Thermostat>();
		thermostatList3.add(new Thermostat("Cold", 72));
		thermostatList3.add(new Thermostat("Heat", 75));
		List<Thermostat> thermostatList4 = new ArrayList<Thermostat>();
		thermostatList4.add(new Thermostat("Heat", 78));		
		return (evt) -> {
			apartmentRepository.save(new Apartment(thermostatList1));
			apartmentRepository.save(new Apartment(thermostatList2));
			apartmentRepository.save(new Apartment(thermostatList3));
			apartmentRepository.save(new Apartment(thermostatList4));	
		};
	}
	/*
	@Bean
	CommandLineRunner init(BuildingRepository buildingRepository) {
		List<Thermostat> thermostatList1 = new ArrayList<Thermostat>();
		thermostatList1.add(new Thermostat("Cold", 72));
		thermostatList1.add(new Thermostat("Heat", 72));
		List<Apartment> apartmentList1 = new ArrayList<Apartment>();
		apartmentList1.add(new Apartment(thermostatList1));
		List<Thermostat> thermostatList2 = new ArrayList<Thermostat>();
		thermostatList2.add(new Thermostat("Cold", 68));
		apartmentList1.add(new Apartment(thermostatList2));
		
		
		List<Thermostat> thermostatList3 = new ArrayList<Thermostat>();
		thermostatList3.add(new Thermostat("Cold", 72));
		thermostatList3.add(new Thermostat("Heat", 72));
		List<Apartment> apartmentList2 = new ArrayList<Apartment>();
		apartmentList2.add(new Apartment(thermostatList1));
		List<Thermostat> thermostatList4 = new ArrayList<Thermostat>();
		thermostatList4.add(new Thermostat("Cold", 68));
		apartmentList2.add(new Apartment(thermostatList4));
				
		return (evt) -> {
			buildingRepository.save(new Building(apartmentList1));
			buildingRepository.save(new Building(apartmentList2));	
		};
	}
*/
}

@RestController
class ThermostatController {
	ThermostatRegistration thermostatRegistration;
	@Autowired
	ThermostatController(ThermostatRegistration thermostatRegistration) {
		this.thermostatRegistration = thermostatRegistration;
	}
	@RequestMapping( path="/thermostat", method = RequestMethod.POST)
	Thermostat register(@RequestBody Thermostat thermostat){
		return thermostatRegistration.register(thermostat);
	}
	@RequestMapping( path="/thermostat", method = RequestMethod.PUT)
	Thermostat update(@RequestBody Thermostat thermostat){
		return thermostatRegistration.update(thermostat);
	}
	@RequestMapping( path="/thermostatList", method = RequestMethod.PUT)
	List<Thermostat> updateAll(@RequestBody List<Thermostat> thermostatList){
		return thermostatRegistration.updateAll(thermostatList);
	}
}

@Entity
class Thermostat {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long thermostatId;
	
	public Thermostat() {}
	public Thermostat(String weatherType, Integer temperature) {
		super();
		this.weatherType = weatherType;
		this.temperature = temperature;
	}
	public Long getThermostatId() {
		return thermostatId;
	}
	public void setThermostatId(Long thermostatId) {
		this.thermostatId = thermostatId;
	}
	public String getWeatherType() {
		return weatherType;
	}
	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}
	public Integer getTemperature() {
		return temperature;
	}
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}
	String weatherType;
	Integer temperature;

	@ManyToOne
	@JoinColumn(name="apartment.thermostatList.thermostat.thermostatId")	
	Apartment apartment;
	public Apartment getApartment() {
		return apartment;
	}
	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

}

@Entity
class Apartment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public Apartment() {}
	@OneToMany(targetEntity = Thermostat.class, mappedBy = "apartment.thermostatList.thermostat.thermostatId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Thermostat> thermostatList;	
	public Apartment(List<Thermostat> thermostatList) {
		super();
		this.thermostatList = thermostatList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Thermostat> getThermostatList() {
		return thermostatList;
	}
	public void setThermostatList(List<Thermostat> thermostatList) {
		this.thermostatList = thermostatList;
	}

}

/*
@Entity
class Building {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long buildingNo;
	public Building() {}
	public Building(List<Apartment> apartmentList) {
		super();
		this.apartmentList = apartmentList;
	}
	public long getBuildingNo() {
		return buildingNo;
	}
	public void setBuildingNo(long buildingNo) {
		this.buildingNo = buildingNo;
	}
	public List<Apartment> getApartmentList() {
		return apartmentList;
	}
	public void setApartmentList(List<Apartment> apartmentList) {
		this.apartmentList = apartmentList;
	}
	List<Apartment> apartmentList;
	
}
*/
@Component
@Lazy
class ThermostatRegistration {
	ThermostatRepository thermostatRepository;
	@Autowired
	ThermostatRegistration(ThermostatRepository thermostatRepository) {
		this.thermostatRepository = thermostatRepository;
	}
	Thermostat register(Thermostat thermostat) {
		Optional<Thermostat> existingThermostat = 
				thermostatRepository.findByThermostatId(thermostat.getThermostatId());
        if (existingThermostat.isPresent()) {
        	throw new RuntimeException("is already exists");
        }
        else {
        	thermostatRepository.save(thermostat);
        }
		return thermostat;		
	}
	Thermostat update(Thermostat thermostat) {
		Optional<Thermostat> existingThermostat = 
				thermostatRepository.findByThermostatId(thermostat.getThermostatId());
        if (existingThermostat.isPresent()) {
        	thermostatRepository.save(thermostat);
        }
        else {
        	throw new RuntimeException("it doesn't exist");
        }
		return thermostat;		
	}
	List<Thermostat> updateAll(List<Thermostat> thermostatList) {
		for (Thermostat thermostat:thermostatList) {
			Optional<Thermostat> existingThermostat = 
					thermostatRepository.findByThermostatId(thermostat.getThermostatId());			
	        if (existingThermostat.isPresent()) {
	        	thermostatRepository.save(thermostat);
	        }
	        else {
	        	throw new RuntimeException("it doesn't exist");
	        }			
		}
		return thermostatList;		
	}
}

@Component
@Lazy
class ApartmentRegistration {
	ApartmentRepository apartmentRepository;
	@Autowired
	ApartmentRegistration(ApartmentRepository apartmentRepository) {
		this.apartmentRepository = apartmentRepository;
	}
	Apartment register(Apartment apartment) {
		Optional<Apartment> existingApartment = 
				apartmentRepository.findApartmentById(apartment.getId());
        if (existingApartment.isPresent()) {
        	throw new RuntimeException("is already exists");
        }
        else {
        	apartmentRepository.save(apartment);
        }
		return apartment;		
	}
	Apartment update(Apartment apartment) {
		Optional<Apartment> existingApartment = 
				apartmentRepository.findApartmentById(apartment.getId());
        if (existingApartment.isPresent()) {
        	apartmentRepository.save(apartment);
        }
        else {
        	throw new RuntimeException("it doesn't exist");
        }
		return apartment;		
	}
	List<Apartment> updateAll(List<Apartment> apartmentList) {
		for (Apartment apartment:apartmentList) {
			Optional<Apartment> existingApartment = 
					apartmentRepository.findApartmentById(apartment.getId());			
	        if (existingApartment.isPresent()) {
	        	apartmentRepository.save(apartment);
	        }
	        else {
	        	throw new RuntimeException("it doesn't exist");
	        }			
		}
		return apartmentList;		
	}
}
/*
@Component
@Lazy
class BuildingRegistration {
	BuildingRepository buildingRepository;
	@Autowired
	BuildingRegistration(BuildingRepository buildingRepository) {
		this.buildingRepository = buildingRepository;
	}
	Building register(Building building) {
		Optional<Building> existingBuilding = 
				buildingRepository.findBuildingById(building.getBuildingNo());
        if (existingBuilding.isPresent()) {
        	throw new RuntimeException("is already exists");
        }
        else {
        	buildingRepository.save(building);
        }
		return building;		
	}
	Building update(Building building) {
		Optional<Building> existingBuilding = 
				buildingRepository.findBuildingById(building.getBuildingNo());
        if (existingBuilding.isPresent()) {
        	buildingRepository.save(building);
        }
        else {
        	throw new RuntimeException("it doesn't exist");
        }
		return building;		
	}
	List<Building> updateAll(List<Building> buildingList) {
		for (Building building:buildingList) {
			Optional<Building> existingBuilding = 
					buildingRepository.findBuildingById(building.getBuildingNo());			
	        if (existingBuilding.isPresent()) {
	        	buildingRepository.save(building);
	        }
	        else {
	        	throw new RuntimeException("it doesn't exist");
	        }			
		}
		return buildingList;		
	}
}
*/
@RepositoryRestResource
@Lazy
interface ThermostatRepository extends JpaRepository<Thermostat, Long> {
	Optional<Thermostat> findByThermostatId(@Param("thermostatId") Long thermostatId);
}

@RepositoryRestResource
@Lazy
interface ApartmentRepository extends JpaRepository<Apartment, Long> {
	Optional<Apartment> findApartmentById(@Param("id") Long id);
}
/*
@RepositoryRestResource
@Lazy
interface BuildingRepository extends JpaRepository<Building, Long> {
	Optional<Building> findBuildingById(@Param("buildingNo") Long buildingNo);
}
*/