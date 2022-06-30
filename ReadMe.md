# Vizsgaremek

## Leírás

Alkalmazás ami nyilván tartja az otthon található ritkán használt tárgyakat(Thing entity).
Amit alkalomszerűen használunk, évente 1-2-szer vagy több évente gyakran elfelejtjük hova is tettük.
Főleg ha nem csak otthon de szüleinknél vagy akár a nyaralónkba is hagyhatunk dolgokat nehezíti a helyzetet,
ha van mindenhol padlás, garázs, garázs padlás, pince több melléképület(Location entity).

---

## Felépítés

### Location

A `Location` entitás a következő attribútumokkal rendelkezik:

* `id` az adatbázis generálja
* `name` a hely neve (kötelező)
* `room` a helyiség neve (enum, kötelező)
* `info` információ a hellyel kapcsolatban (opcionális)
* `things` a tárgyak listája

Végpontok:

| HTTP metódus | Végpont                       | Leírás                         |
|--------------|-------------------------------|--------------------------------|
| GET          | `/api/locations`              | Az összes hely lekérdezése     |
| POST         | `/api/locations`              | Új hely mentése                |
| PUT          | `/api/locations/{id}`         | A hely információ frissítése   |
| DELETE       | `/api/locations/{id}`         | Hely törlése ID alapján        |
| GET          | `/api/locations/names`        | Az összes hely nevének listája |
| GET          | `/api/locations/{name}/rooms` | A hely összes helyisége        |

---

### Thing

A `Thing ` entitás a következő attribútumokkal rendelkezik:

* `id` az adatbázis generálja
* `type` a tárgy típusa (enum, kötelező)
* `pictures` képeket tartalmazó lista (opcionális)
* `description` részletes leírás a tárgyról (kötelező)
* `updated` az el/át-helyezés dátuma (automatikus)
* `location` a hely ahol található (kötelező)

A `Location` és a `Thing` entitások között kétirányú, 1-n kapcsolat van.

Végpontok:

| HTTP metódus | Végpont                        | Leírás                                      |
|--------------|--------------------------------|---------------------------------------------|
| GET          | `/api/things`                  | A tárgyak szűrése leírás and típus alapján  |
| POST         | `/api/things`                  | Tárgy létrehozása                           |
| DELETE       | `/api/things/{id}`             | Tárgy törlése ID alapján                    |
| GET          | `/api/things/{id}`             | Tárgy keresése ID alapján                   |
| PUT          | `/api/things/{id}/description` | A leírás frissítése a tárgy ID-ja alapján   |
| PUT          | `/api/things/{id}/location`    | A tárgy helyének módosítása                 |
| POST         | `/api/things/{id}/picture`     | Kép hozzáadása tárgyhoz                     |
| GET          | `/api/things/last-moved`       | Utoljára áthelyezett tárgyak                |                          |

---

## Technológiai részletek

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal,
Java Spring backenddel, REST webszolgáltatásokkal, SWAGGER UI-val

---
