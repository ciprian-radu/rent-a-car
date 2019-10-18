module ro.ciprianradu.rentacar.usecases {
    requires ro.ciprianradu.rentacar.entities;

    exports ro.ciprianradu.rentacar.usecases.gateways;
    exports ro.ciprianradu.rentacar.usecases.searchvehicles;
    exports ro.ciprianradu.rentacar.usecases.rentvehicle;
    exports ro.ciprianradu.rentacar.usecases.retrievelocations;
    exports ro.ciprianradu.rentacar.usecases.registerlocation;
    exports ro.ciprianradu.rentacar.usecases.registervehicle;
    exports ro.ciprianradu.rentacar.usecases.registerrenter;
}