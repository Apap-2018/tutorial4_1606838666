package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.CarModel;
import com.apap.tutorial4.model.DealerModel;
import com.apap.tutorial4.service.CarService;
import com.apap.tutorial4.service.DealerService;

/**
 * 
 * @author faisalridwan
 * CarController
 */
@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerID}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerID") Long dealerID, Model model) {
		CarModel car = new CarModel();
		DealerModel dealer = dealerService.getDealerDetailById(dealerID).get();
		car.setDealer(dealer);
		
		model.addAttribute("car", car);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerID}", method = RequestMethod.POST)
	private String addCarSubmit(
			@ModelAttribute CarModel car, 
			@PathVariable(value = "dealerID") Long dealerID,
			Model model
			) {
		carService.addCar(car);
		model.addAttribute("dealerId", dealerID);
		return "redirect:/dealer/view?dealerId=" + Long.toString(dealerID);
	}
	
	@RequestMapping(value = "/car/delete/{dealerId}/{carId}", method = RequestMethod.POST)
	private String deleteCar(
			@PathVariable(value = "dealerId") Long dealerId,
			@PathVariable(value = "carId") Long carId
			) {
		carService.deleteCar(carId);
		return "redirect:/dealer/view?dealerId=" + Long.toString(dealerId);
	}
	
	@RequestMapping(value = "/car/edit/{dealerId}/{carId}", method = RequestMethod.GET)
	private String updateCar(
			@PathVariable(value = "dealerId") Long dealerId,
			@PathVariable(value = "carId") Long carId,
			Model model
			) {
		CarModel car = carService.getCarDetailById(carId).get();
		
		String brand = car.getBrand();
		String type = car.getType();
		Long price = car.getPrice();
		Integer amount = car.getAmount();

		model.addAttribute("dealerId", dealerId);
		model.addAttribute("carId", carId);
		model.addAttribute("brand", brand);
		model.addAttribute("type", type);
		model.addAttribute("price", price);
		model.addAttribute("amount", amount);
		return "edit-car";
	}
	
	@RequestMapping(value = "/car/edit/{dealerId}/{carId}", method = RequestMethod.POST)
	private String updateCar(
			@PathVariable(value = "dealerId") Long dealerId,
			@PathVariable(value = "carId") Long carId,
			@ModelAttribute CarModel newCar
			) {
		carService.editCar(newCar, carId);
		return "redirect:/dealer/view?dealerId=" + Long.toString(dealerId);
	}
	
}
