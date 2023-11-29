create table rol
(
    id_rol      serial
        primary key,
    nombre      varchar(40) not null,
    descripcion varchar(160),
    estado      varchar(1)  not null
);

create table usuario
(
    id             serial
        primary key,
    correo         varchar(256) not null
        unique,
    clave          varchar(256) not null,
    telefono       varchar(256) not null,
    direccion      varchar(256),
    id_ciudad      integer,
    cod_postal     varchar(10),
    nombre         varchar(40),
    apellido       varchar(40),
    fecha_creacion date,
    estado         varchar(1),
    id_rol         integer
        references rol
);
INSERT INTO public.rol (id_rol, nombre, descripcion, estado) VALUES (1, 'ROL_TECNICO_NVL1', null, 'A');
INSERT INTO public.rol (id_rol, nombre, descripcion, estado) VALUES (2, 'ROL_TECNICO_NVL2', null, 'A');
INSERT INTO public.rol (id_rol, nombre, descripcion, estado) VALUES (3, 'ROL_TECNICO_NVL3', null, 'A');
INSERT INTO public.rol (id_rol, nombre, descripcion, estado) VALUES (4, 'ROL_COORDINADOR_SOPORTE', null, 'A');
INSERT INTO public.rol (id_rol, nombre, descripcion, estado) VALUES (5, 'ROL_ADMIN', null, 'A');
INSERT INTO public.rol (id_rol, nombre, descripcion, estado) VALUES (6, 'ROL_DISTRIBUIDOR', null, 'A');
INSERT INTO public.rol (id_rol, nombre, descripcion, estado) VALUES (7, 'ROL_CLIENTE', null, 'A');





