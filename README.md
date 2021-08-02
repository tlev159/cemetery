### VIZSGAREMEK

## Temetői nyilvántartás

# A projekt célja

Egy város temetőjének adminisztrációs nyilvántartása. Az ott található sírok parcellákra,
ezek pedig sorokra és oszlopokra vannak osztva. A megváltójának adadtait
(név, cím, elérhetőség, megváltás dátuma, a sír fajtája), valamint a sírokban
eltemetett elhunytak adatainak nyilvántartása
(név, édesanyja neve, születési dátum, temetés dátuma).

A következők megvalósítása szükséges:

- három tábla létrehozása, melyek közül egymással egy-több kapcsolattal
  rendelkeznek
  (egy parcellának egy bérlője lehet, és egy sírban több elhunyt is el
  lehet temetve);

- táblák nevei rendre:
  parcellák (graves); sír megváltója (leaseholders), elhunytak (obituaries);

Feltételek az egyes táblákkal kapcsolatban:

- parcellák:
    - a parcellák nevei egy karakteres betűk (nem lehetnek üresek és maximum 3 karakterből állhatnak)
    - a sor és oszlop adatok nem lehetnek üresek és maximum három számjegyből állhatnak

- bérlők (sír megváltóinak adatai):
    - név (nem lehet üres, maximum 200 karakter)
    - cím (nem lehet üres, maximum 255 karakter)
    - telefonszám (nem lehet üres, maximum 100 karakter)
    - megváltás dátuma (nem lehet üres)
    - sír fajtája (nem lehet üres, maximum 50 karakter)

- elhunytak:
    - név (nem lehet üres, maximum 200 karakter)
    - édesanyja neve (nem lehet üres, maximum 200 karakter)
    - születési dátum (nem lehet üres)
    - temetés dátuma (nem lehet üres és nem lehet korábbi a pillanatnyi dátumtól, mivel utólagos rögzítés történik)

A nyilvántartandó adatokkal a következőket lehet megvalósítani:

- PARCELLÁK (végpontja: `/graves`):

  lehessen:
    - új parcellákat felvenni, mivel a temető bővítése elképzelhető a jövőben
    - lekérni a temetőben lévő teljes parcellalistát (opcióként egy bizonyos parcella listáját sorokkal, oszlopokkal)
    - az adatait módosítani
    - egy konkrét sírt törölni
    - az összeset törölni

- BÉRLŐK (végpontja: `/leaseholders`):

  lehessen:
    - új bérlőt felvenni
    - a bérlőket listázni
    - egy konkrét bérlőre rákeresni
    - a bérlő adatait módosítani
    - egy bérlőt törölni
    - az összse bérlőt törölni

- ELHUNYTAK (végpontja: `/obituaries`):

  lehessen:
    - új elhunytat felvenni
    - az elhunytakat listázni
    - egy konkrét elhunytra rákeresni
    - az elhunyt adatait módosítani
    - egy elhunytat törölni
    - az összes elhunytat törölni

## Parcellák - bérlők kapcsolat

Egy sírt a parcellában meg lehet váltani, vagyis bérlőt lehet hozzárendelni.
Ezt az `/api/grave/{id}/leaseholder` címen lehet.
A parcella létrehozásakor a bérlők még üresek.

## Bérlők - elhunytak kapcsolat

A bérlő létrehozásakor az elhunytak listája még üres, ezeket később kell felvenni.
A halottakat az `/api/grave/{id}/obituaries` címen lehet felvenni.


Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal, Java Spring backenddel,
REST webszolgáltatásokkal.
- Az adatbázis kezelő réteg megvalósítása Spring Data JPA-val (`Repository`)
- A scriptek Flayway segítségével készülnek verziókövetéssel
- Az üzleti logikai réteg `@Service` osztályokkal kerül megvalósításra
- Az teszek integrációs tesztek (TestRestTemplate, szükséges a 80 %-os tesztlefedettség)
- A Controller réteg megvalósítása a RESTful API implementálására. Az API végpontoknak a `/api` címen kell, hogy elérhetők legyenek
- Szükséges a hibakezelés valamint a validáció
- Swagger felületen történő tesz is szükséges
- HTTP fájl írása a funkciók könnyebb tesztelhetőségéhez
- Dockerfile is létrehozásra kerül, mivel az alkalmazás később dockerből futtatható kell, hogy legyen

