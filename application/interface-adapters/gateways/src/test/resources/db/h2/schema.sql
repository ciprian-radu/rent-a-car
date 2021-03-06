DROP ALL OBJECTS;

CREATE SCHEMA IF NOT EXISTS RENT_A_CAR;

SET SCHEMA RENT_A_CAR;

/************************************************/
/* LOCATION  */
/************************************************/

CREATE TABLE LOCATION (
    NAME          VARCHAR2(100)   NOT NULL,
    ADDRESS       VARCHAR2(500)
);

ALTER TABLE LOCATION ADD CONSTRAINT LOCATION_PK PRIMARY KEY (NAME);

/************************************************/
/* RENTER  */
/************************************************/

CREATE TABLE RENTER (
    FIRST_NAME          VARCHAR2(100)   NOT NULL,
    LAST_NAME           VARCHAR2(100)   NOT NULL,
    EMAIL               VARCHAR2(100)   NOT NULL,
    TELEPHONE_NUMBER    VARCHAR2(20)
);

ALTER TABLE RENTER ADD CONSTRAINT RENTER_PK PRIMARY KEY (EMAIL);

/************************************************/
/* VEHICLE_BRAND  */
/************************************************/

CREATE TABLE VEHICLE_BRAND (
    NAME          VARCHAR2(100)   NOT NULL,
);

ALTER TABLE VEHICLE_BRAND ADD CONSTRAINT VEHICLE_BRAND_PK PRIMARY KEY (NAME);

/************************************************/
/* VEHICLE_TYPE  */
/************************************************/

CREATE TABLE VEHICLE_TYPE (
    TYPE          VARCHAR2(100)   NOT NULL,
);

ALTER TABLE VEHICLE_TYPE ADD CONSTRAINT VEHICLE_TYPE_PK PRIMARY KEY (TYPE);

/************************************************/
/* VEHICLE_MODEL  */
/************************************************/

CREATE TABLE VEHICLE_MODEL (
    NAME                        VARCHAR2(100)   NOT NULL,
    AC                          BOOLEAN,
    AUTOMATIC_TRANSMISSION      BOOLEAN,
    SEATS                       INTEGER
);

ALTER TABLE VEHICLE_MODEL ADD CONSTRAINT VEHICLE_MODEL_PK PRIMARY KEY (NAME);

/************************************************/
/* VEHICLE  */
/************************************************/

CREATE TABLE VEHICLE (
    ID          VARCHAR2(100)   NOT NULL,
    TYPE        VARCHAR2(100)   NOT NULL,
    BRAND       VARCHAR2(100)   NOT NULL,
    MODEL       VARCHAR2(100)   NOT NULL,
    RATE        DECIMAL                 ,
    LOCATION    VARCHAR2(100)   NOT NULL
);

ALTER TABLE VEHICLE ADD CONSTRAINT VEHICLE_PK PRIMARY KEY (ID);
ALTER TABLE VEHICLE ADD FOREIGN KEY (TYPE) REFERENCES VEHICLE_TYPE (TYPE);
ALTER TABLE VEHICLE ADD FOREIGN KEY (BRAND) REFERENCES VEHICLE_BRAND (NAME);
ALTER TABLE VEHICLE ADD FOREIGN KEY (MODEL) REFERENCES VEHICLE_MODEL (NAME);
ALTER TABLE VEHICLE ADD FOREIGN KEY (LOCATION) REFERENCES LOCATION (NAME);

/************************************************/
/* RESERVATION  */
/************************************************/

CREATE TABLE RESERVATION (
    RENTER              VARCHAR2(100)               NOT NULL,
    VEHICLE             VARCHAR2(100)               NOT NULL,
    PICKUP_DATE         TIMESTAMP WITH TIME ZONE    NOT NULL,
    PICKUP_LOCATION     VARCHAR2(100)               NOT NULL,
    RETURN_DATE         TIMESTAMP WITH TIME ZONE    NOT NULL,
    RETURN_LOCATION     VARCHAR2(100)               NOT NULL
);

ALTER TABLE RESERVATION ADD CONSTRAINT RESERVATION_PK PRIMARY KEY (RENTER, VEHICLE, PICKUP_DATE);
ALTER TABLE RESERVATION ADD FOREIGN KEY (RENTER) REFERENCES RENTER (EMAIL);
ALTER TABLE RESERVATION ADD FOREIGN KEY (VEHICLE) REFERENCES VEHICLE (ID);
ALTER TABLE RESERVATION ADD FOREIGN KEY (PICKUP_LOCATION) REFERENCES LOCATION (NAME);
ALTER TABLE RESERVATION ADD FOREIGN KEY (RETURN_LOCATION) REFERENCES LOCATION (NAME);