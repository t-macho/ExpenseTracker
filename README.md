# ExpenseTracker
Program je určen ke snadnému sledování příjmů a výdajů. Jednotlivé transakce je možno libovolně přidávat, upravovat a odebírat.

### Grafické rozhraní
Hlavní okno se skládá ze tří částí - tabulky, ovládacích prvků a přehledu.

#### Tabulka
V tabulce se objevují přidané transakce. Tabulka má tři sloupce - název, částku a datum.
Je možné transakce setřídit podle libovolného sloupce pouhým kliknutím na něj.
Transakce jsou barevně rozlišeny podle toho, jestli se jedná o příjem (zelená), nebo výdaj (červená).
Dvojklikem na transakci se otevře nové okno, ve kterém je možné transakci upravit. Je zde také k nahlédnutí poznámka.
Transakci je v tomto okně také možné odstranit.

#### Ovládací prvky
Napravo nahoře od tabulky se nachází trojice tlačítek a rozbalovací seznam.
Tlačítka postupně umožňují transakce přidávat, ukládat a načítat.
Rozbalovací seznam umožňuje dle zvolené položky filtrovat jen některé transakce (vše, jen příjmy, jen výdaje).
Po kliknutí na tlačítko `Přidat transakci` se otevře nové okno, ve kterém uživatel vyplní detaily transakce,
přičemž název, částka a datum jsou povinné.

#### Přehled
Poslední část hlavního okna je přehled "stavu účtu". Nachází se zde součet příjmů, výdajů a celkový zůstatek.

### Ukládání/načítání
Po stisku tlačítka `Uložit` dojde k uložení všech transakcí, které se právě nacházejí v tabulce do souboru `transactions.csv`
nacházejícího se v kořenovém adresáři programu.
Stiskem tlačítka `Načíst` se program pokusí načíst uložené transakce ze souboru `transactions.csv`. Přitom také smaže veškerý obsah tabulky.
Formát souboru je následující: `typ,název,částka,datum,poznámka`, kde `typ` je 0 pokud se jedná o výdaj, 1 pro příjem.
Datum je ve formátu `dd.mm.yyyy`, při zadání data v chybném formátu nebude soubor načten.

### Poznámka
V repu je přiložen vzorový soubor `transactions.csv`
