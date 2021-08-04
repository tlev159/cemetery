### VIZSGAREMEK

## Temetői nyilvántartás

# A projekt célja

Egy város temetőjének adminisztrációs nyilvántartása. Az ott található sírok parcellákra,
ezek pedig sorokra és oszlopokra vannak osztva. A megváltójának adadtait
(név, cím, elérhetőség, megváltás dátuma, a sír fajtája), valamint a sírokban
eltemetett elhunytak adatainak nyilvántartása
(név, édesanyja neve, születési dátum, temetés dátuma).

A következők megvalósítása szükséges:

- három tábla létrehozása, melyekben egymással egy-egy, valamint egy-több kapcsolattal
  rendelkeznek
  (egy parcellának egy bérlője lehet, és egy sírban több elhunyt is el
  lehet temetve);

- táblák nevei rendre:
  parcellák (graves); sír megváltója/bérlője (leaseholders), elhunytak (obituaries);

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
    - temetés dátuma (nem lehet üres)

A nyilvántartandó adatokkal a következőket lehet megvalósítani:

**_- PARCELLÁK (végpontja: `api/graves`):_**

  

    `POST:` - új sír felvétele (mivel a temető bővítése elképzelhető a jövőben);
    
    `PUT` (`/{id}` végponttal): - a sír adatainak módosítása azonosító alapján;
    
    `GET`: le lehet kérni a temetőben lévő teljes parcellalistát;

    `GET`(`/{id}` végponttal): id alapján lekérdezni a sír adatait (név, sor, oszlop);

    `GET`(`/parcel` végponttal): le lehet kérni paraméterek alapján (opcióként egy bizonyos parcella listáját, illetve annak maghatározott sorában lévőket);

    `DELETE`(`/{id}` végponttal): id alapján lehet törölni;

    `DELETE`: az összes sírt lehet törölni.


_**- BÉRLŐK (végpontja: `api/leaseholders`):**_

    `POST`: új bérlő felvétele;
    
    `PUT` (`/{id}` végponttal): - a bérlő adatainak módosítása azonosító alapján;
    
    `GET`: le lehet kérni a temetőben lévő teljes parcellalistát (opcionálisan név alapján);

    `GET`(`/{id}` végponttal): id alapján lekérdezni a bérlő adatait;

    `DELETE`(`/{id}` végponttal): id alapján lehet törölni;

    `DELETE`: az összes bérlőt lehet törölni.
  

**_- ELHUNYTAK (végpontja: `api/obituaries`):_**

    `POST`: új elhunyt felvétele;
    
    `PUT` (`/{id}` végponttal): - a elhunyt adatainak módosítása azonosító alapján;
    
    `GET`: le lehet kérni a temetőben lévő teljes elhunytlistát (opcionálisan név alapján);

    `GET`(`/{id}` végponttal): id alapján lekérdezni a elhunyt adatait;

    `DELETE`(`/{id}` végponttal): id alapján lehet törölni;

    `DELETE`: az összes bérlőt lehet törölni.
  

## Sírok - bérlők kapcsolata

Egy sírt a parcellában meg lehet váltani, vagyis bérlőt lehet hozzárendelni.
Ez a bérlő felvételekor történik a sír `id`-jának megadásával.

## Sírok - elhunytak kapcsolata

Az elhunytak felvételekor megadásra kerül a sír azonosítója, ezzel történik a két entitás közötti kapcsolat létrehozása.

## Megvalósítás

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal, Java Spring backenddel,
REST webszolgáltatásokkal.
- Az adatbázis kezelő réteg megvalósítása Spring Data JPA-val `(Repository)`
- A scriptek Flyway segítségével készültek verziókövetéssel
- Az üzleti logikai réteg `(Service)` osztályokkal kerül megvalósításra
- Az teszek integrációs tesztek (TestRestTemplate, a tesztlefedettség 80 és 90 % közötti)
- A RESTful API implementálása `Controller` réteg megvalósításával történt. Az API végpontok az `/api`,
  illetve minden entitáshoz a saját műveletekhez tartozó cím tartozik
- Hibakezelés, valamint a validáció is alkalmazásra  került
- Swagger felületen történő tesz is megvalósításra került
- HTTP fájlok mindhárom entitáshoz írásra kerültek a funkciók könnyebb tesztelhetőségéhez
- Dockerfile is létrehozásra került, hogy az alkalmazás később dockerből futtatható lehessen


