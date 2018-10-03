package com.apap.tutorial4.service;

import com.apap.tutorial4.model.CarModel;

/**
 * 
 * @author faisalridwan
 * CarService
 */
public interface CarService {
	void addCar(CarModel car);
	void deleteCar(long carId);
}
