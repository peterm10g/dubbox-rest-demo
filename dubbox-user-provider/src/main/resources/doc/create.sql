drop table if exists tsc_vehicle_owner;
drop table if exists tsc_vehicle_profile;

/*==============================================================*/
/* Table: tsc_vehicle_owner                                     */
/*==============================================================*/
create table tsc_vehicle_owner
(
   user_id              varchar(32) not null ,
   email                varchar(32),
   user_name            varchar(32),
   nick_name            varchar(32),
   gender               varchar(1),
   age                  integer,
   user_identity        varchar(32),
   user_identity_type   varchar(1),
   birthday             date,
   contact_phone        varchar(32),
   emergency_contact_phone varchar(32),
   address              varchar(100),
   vin                  varchar(17),
   vrn                  varchar(32),
   dealer_id            varchar(32),
   purchase_date        date,
   additional_information varchar(200),
   CONSTRAINT tsc_vehicle_owner_pkey PRIMARY KEY(user_id)
);

COMMENT ON TABLE tsc_vehicle_owner IS 'Geely T service user';
COMMENT ON COLUMN tsc_vehicle_owner.user_id IS 'Geely T service user name identity';
COMMENT ON COLUMN tsc_vehicle_owner.email IS 'Email address of T service user. Unique in Geely CSP';
COMMENT ON COLUMN tsc_vehicle_owner.nick_name IS 'Nick name of user';
COMMENT ON COLUMN tsc_vehicle_owner.gender IS '0 female, 1 male';
COMMENT ON COLUMN tsc_vehicle_owner.user_identity_type IS '0, Passport 1, Citizen 2, others';
COMMENT ON COLUMN tsc_vehicle_owner.vrn IS 'Vehicle Registration Number';

/*==============================================================*/
/* Table: tsc_vehicle_profile                                   */
/*==============================================================*/
create table public.tsc_vehicle_profile
(
   vin                  varchar(17) not null,
   model_code           varchar(30),
   model_year           varchar(20),
   destination          varchar(500),
   eol_code             varchar(32),
   color_code           varchar(512),
   tem_id               varchar(32),
   ihu_id               varchar(32),
   sim_id               varchar(32),
   simid                varchar(32),
   msisdn               varchar(32),
   tem_protocol         varchar(32),
   eol_time             date,
   plant_code           varchar(32),
   transmission_type    varchar(32),
   imei                 varchar(32),
   tem_version          varchar(11),
   engine_type          varchar(64),
   CONSTRAINT tsc_vehicle_profile_pkey PRIMARY KEY(vin)
)
WITH (oids = false);

COMMENT ON COLUMN public.tsc_vehicle_profile.vin IS 'identify';
COMMENT ON COLUMN public.tsc_vehicle_profile.model_year IS 'Model manufacture year';
COMMENT ON COLUMN public.tsc_vehicle_profile.eol_code IS 'EOL (End of Line) code support a detailed description of the vehicle like fuel type';
COMMENT ON COLUMN public.tsc_vehicle_profile.color_code IS 'Vehicle Body Color';
COMMENT ON COLUMN public.tsc_vehicle_profile.tem_id IS 'The telematics embedded Model device id, come from the TEM vendor';
COMMENT ON COLUMN public.tsc_vehicle_profile.ihu_id IS 'IHU identity';
COMMENT ON COLUMN public.tsc_vehicle_profile.sim_id IS 'ICCID';
COMMENT ON COLUMN public.tsc_vehicle_profile.simid IS 'IMSI';
COMMENT ON COLUMN public.tsc_vehicle_profile.msisdn IS 'Phone number';
COMMENT ON COLUMN public.tsc_vehicle_profile.tem_protocol IS 'TEM version TEM protocol format';
COMMENT ON COLUMN public.tsc_vehicle_profile.transmission_type IS 'Identify transmission type, E.g. 4-speed-AT, 5-speed-AT, 5-speed-MT, etc.';
COMMENT ON COLUMN public.tsc_vehicle_profile.imei IS 'The IMEI (14 decimal digits plus a check digit) or IMEISV (16 digits) includes information on the origin, model, and serial number of the device. It''s from TEM vendor';
COMMENT ON COLUMN public.tsc_vehicle_profile.tem_version IS 'TEM''s software edition and production details.';
COMMENT ON COLUMN public.tsc_vehicle_profile.engine_type IS 'Engine type code';
