package ro.ciprianradu.rentacar.usecases.registervehicle;

import java.util.Optional;
import ro.ciprianradu.rentacar.entity.Vehicle;
import ro.ciprianradu.rentacar.entity.VehicleBrand;
import ro.ciprianradu.rentacar.entity.VehicleModel;
import ro.ciprianradu.rentacar.entity.VehicleType;
import ro.ciprianradu.rentacar.usecases.Context;

/**
 * Allows users of the system to register vehicles which may be rented.
 */
public class RegisterVehicleUseCase implements RegisterVehicleInputPort {

    @Override
    public void registerVehicle(final RegisterVehicleInputData inputData, final RegisterVehicleOutputPort outputPort) {
        Vehicle vehicle = new Vehicle(inputData.getId());
        setVehicleType(inputData, vehicle);
        setVehicleBrand(inputData, vehicle);
        setVehicleModel(inputData, vehicle);
        vehicle.setRate(inputData.getRate());
        vehicle.setLocation(inputData.getLocation());

        boolean vehicleRegistered = false;
        final Optional<Vehicle> optionalVehicle = Context.vehicleGateway.findById(vehicle.getId());
        if (!optionalVehicle.isPresent()) {
            Context.vehicleGateway.save(vehicle);
            vehicleRegistered = true;
        }

        RegisterVehicleOutputData registerVehicleOutputData = new RegisterVehicleOutputData(vehicleRegistered);
        outputPort.present(registerVehicleOutputData);
    }

    private void setVehicleType(final RegisterVehicleInputData inputData, final Vehicle vehicle) {
        final Optional<VehicleType> vehicleTypeOptional = Context.vehicleTypeGateway.findByType(inputData.getType());
        if (vehicleTypeOptional.isPresent()) {
            vehicle.setType(vehicleTypeOptional.get());
        } else {
            VehicleType vehicleType = new VehicleType();
            vehicleType.setType(inputData.getType());
            Context.vehicleTypeGateway.save(vehicleType);
            vehicle.setType(vehicleType);
        }
    }

    private void setVehicleBrand(final RegisterVehicleInputData inputData, final Vehicle vehicle) {
        final Optional<VehicleBrand> vehicleBrandOptional = Context.vehicleBrandGateway.findByBrand(inputData.getBrand());
        if (vehicleBrandOptional.isPresent()) {
            vehicle.setBrand(vehicleBrandOptional.get());
        } else {
            VehicleBrand vehicleBrand = new VehicleBrand();
            vehicleBrand.setName(inputData.getBrand());
            Context.vehicleBrandGateway.save(vehicleBrand);
            vehicle.setBrand(vehicleBrand);
        }
    }

    private void setVehicleModel(final RegisterVehicleInputData inputData, final Vehicle vehicle) {
        final Optional<VehicleModel> vehicleModelOptional = Context.vehicleModelGateway.findByModel(inputData.getModel());
        if (vehicleModelOptional.isPresent()) {
            vehicle.setModel(vehicleModelOptional.get());
        } else {
            VehicleModel vehicleModel = new VehicleModel();
            vehicleModel.setName(inputData.getModel());
            Context.vehicleModelGateway.save(vehicleModel);
            vehicle.setModel(vehicleModel);
        }
    }
}
