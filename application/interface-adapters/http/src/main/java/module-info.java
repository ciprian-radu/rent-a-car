module ro.ciprianradu.rentacar.http {
    requires ro.ciprianradu.rentacar.usecases;

    exports ro.ciprianradu.rentacar.http.searchvehicles;
    exports ro.ciprianradu.rentacar.http.rentvehicle;
    exports ro.ciprianradu.rentacar.http.retrievelocations;
    exports ro.ciprianradu.rentacar.http.registerlocation;
    exports ro.ciprianradu.rentacar.http.registervehicle;
    exports ro.ciprianradu.rentacar.http.registerrenter;
}