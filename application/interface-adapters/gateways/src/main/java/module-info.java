module ro.ciprianradu.rentacar.gateways {
    requires ro.ciprianradu.rentacar.entities;
    requires ro.ciprianradu.rentacar.usecases;
    requires java.sql;
    requires spring.jdbc;

    exports ro.ciprianradu.rentacar.gateways;
}